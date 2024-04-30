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
public class LoginDto {
    @Schema(description = "Имя пользователя", example = "user123")
    private String username;
    @Schema(description = "Пароль", example = "password123")
    private String password;
}
