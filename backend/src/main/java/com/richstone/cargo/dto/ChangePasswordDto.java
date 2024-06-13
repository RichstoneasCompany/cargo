package com.richstone.cargo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePasswordDto {
    @Schema(description = "Информация о текущей поездке")
    private String newPassword;
    @Schema(description = "Информация о грузе в текущей поездке")
    private String confirmationPassword;
}