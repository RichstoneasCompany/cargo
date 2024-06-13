package com.richstone.cargo.service.impl;

import com.richstone.cargo.configuration.security.JwtService;
import com.richstone.cargo.configuration.external.TwilioConfig;
import com.richstone.cargo.dto.*;
import com.richstone.cargo.exception.OtpNotFoundException;
import com.richstone.cargo.model.Otp;
import com.richstone.cargo.model.User;
import com.richstone.cargo.model.types.OtpStatus;
import com.richstone.cargo.repository.OtpRepository;
import com.richstone.cargo.service.OtpService;
import com.twilio.rest.api.v2010.account.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.twilio.type.PhoneNumber;

import java.text.DecimalFormat;
import java.util.Random;


@Service
@RequiredArgsConstructor
@Slf4j
public class OtpServiceImpl implements OtpService {

    private final TwilioConfig twilioConfig;
    private final UserServiceImpl userServiceImpl;
    private final OtpRepository otpRepository;
    private final AuthenticationServiceImpl authenticationService;
    private final JwtService jwtService;

    @Override
    public OtpResponseDto sendOTPForPasswordReset(OtpRequestDto OtpRequestDto) {
        OtpResponseDto otpResponseDto;

        User userByPhone = userServiceImpl.findByPhone(OtpRequestDto.getPhoneNumber());
        String otp = null; // Инициализируем otp значением null

        try {
            PhoneNumber to = new PhoneNumber(OtpRequestDto.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
            otp = generateOTP(); // Генерируем otp

            saveUserOtp(userByPhone, otp, OtpStatus.DELIVERED); // Сохраняем otp в базе данных

            String otpMessage = "Dear " + userByPhone.getFirstname() + ", Your OTP is " + otp + ". Use this password to sign into your account.";
            Message message = Message.creator(to, from, otpMessage).create();

            otpResponseDto = new OtpResponseDto(OtpStatus.DELIVERED, "OTP sent successfully. " + otpMessage);

        } catch (Exception ex) {
            otpResponseDto = new OtpResponseDto(OtpStatus.FAILED, "Error: " + ex.getMessage());
        }

        return otpResponseDto;
    }


    @Override
    public void saveUserOtp(User theUser, String otp, OtpStatus status) {
        var code = new Otp(otp, theUser, status);
        otpRepository.save(code);
    }

    @Override
    public AuthenticationResponse validateOtp(OtpValidationDto otpValidationDto) {
        Otp code = findByCode(otpValidationDto.getOneTimePassword());
        Long userId = code.getUser().getId();
        AuthenticationResponse authenticationResponse = generateJwt(userId);
        code.setOtpStatus(OtpStatus.VERIFIED);
        otpRepository.save(code);
        return authenticationResponse;
    }

    public AuthenticationResponse generateJwt(Long id) {
        User userById = userServiceImpl.findById(id);
        var user = userServiceImpl.findByUsername(userById.getUsername());
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        authenticationService.revokeAllUserTokens(user);
        authenticationService.saveUserToken(user, jwtToken);
        DriverDto driverDto = DriverDto.builder()
                .name(user.getFirstname())
                .surname(user.getLastname())
                .build();
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(driverDto)
                .build();
    }

    private String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }

    public Otp findByCode(String otp) {
        log.info("Finding otp : {}", otp);

        return otpRepository.findByCode(otp)
                .orElseThrow(() -> {
                    log.error("Otp not found with otp: {}", otp);
                    return new OtpNotFoundException("Otp not found ");
                });
    }

    public void checkOtp(OtpValidationDto otpValidationDto) {
        Otp code = findByCode(otpValidationDto.getOneTimePassword());
        code.setOtpStatus(OtpStatus.VERIFIED);
        otpRepository.save(code);
    }
}