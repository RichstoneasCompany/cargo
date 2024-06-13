package com.richstone.cargo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverLoginDto {
    @Schema(description = "Номер телефона водителя.")
    private String phone;
    @Schema(description = "Пароль водителя")
    private String password;
}