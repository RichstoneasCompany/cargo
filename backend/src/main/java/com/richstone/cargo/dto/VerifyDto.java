package com.richstone.cargo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyDto {
    @Schema(description = "Имя пользователя", example = "driver01")
    private String username;

    @Schema(description = "Телефон", example = "+79991234567")
    private String phone;

    @Schema(description = "Email", example = "driver01@example.com")
    private String email;

}
