package com.richstone.cargo.controllers;

import com.richstone.cargo.dto.*;
import com.richstone.cargo.exception.OtpNotFoundException;
import com.richstone.cargo.service.impl.OtpServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
@Tag(name = "OtpController", description = "API для управления OTP")
public class OtpController {
    private final OtpServiceImpl otpServiceImpl;

    @PostMapping(value = "/sendOTP")
    @Operation(summary = "Отправка OTP",
            description = "Отправляет одноразовый пароль пользователю для сброса пароля")
    public OtpResponseDto sendSMS(@RequestBody OtpRequestDto otpRequestDto) {
        return otpServiceImpl.sendOTPForPasswordReset(otpRequestDto);
    }

    @PostMapping("/validateOTP")
    @Operation(summary = "Проверка OTP",
            description = "Проверяет введенный одноразовый пароль и возвращает результат авторизации")
    public AuthenticationResponse validateOtp(@RequestBody OtpValidationDto otpValidationDto) {
        return otpServiceImpl.validateOtp(otpValidationDto);
    }

    @PostMapping("/checkOTP")
    @Operation(summary = "Проверка OTP",
            description = "Проверяет введенный одноразовый пароль чтобы зарегистрироватся")
    public ResponseEntity<String> checkOtp(@RequestBody OtpValidationDto otpValidationDto) {
        try {
            otpServiceImpl.checkOtp(otpValidationDto);
            return ResponseEntity.ok("OTP verified successfully");
        } catch (OtpNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}