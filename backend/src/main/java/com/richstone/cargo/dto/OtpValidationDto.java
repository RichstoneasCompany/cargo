package com.richstone.cargo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpValidationDto {

    @Schema(description = "Одноразовый пароль, введенный пользователем для проверки", example = "123456")
    private String oneTimePassword;
}