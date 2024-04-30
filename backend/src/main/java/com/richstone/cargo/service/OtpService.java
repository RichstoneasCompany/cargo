package com.richstone.cargo.service;

import com.richstone.cargo.dto.AuthenticationResponse;
import com.richstone.cargo.dto.OtpRequestDto;
import com.richstone.cargo.dto.OtpResponseDto;
import com.richstone.cargo.dto.OtpValidationDto;
import com.richstone.cargo.model.Otp;
import com.richstone.cargo.model.User;
import com.richstone.cargo.model.types.OtpStatus;

public interface OtpService {
    OtpResponseDto sendOTPForPasswordReset(OtpRequestDto OtpRequestDto);
    void saveUserOtp(User theUser, String otp, OtpStatus status);
    AuthenticationResponse validateOtp(OtpValidationDto otpValidationDto);
    Otp findByCode(String otp);
}
