package com.richstone.cargo.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpRequestDto {
    @Schema(description = "Номер телефона пользователя, на который будет отправлен OTP", example = "+77071234567")
    private String phoneNumber;
}
