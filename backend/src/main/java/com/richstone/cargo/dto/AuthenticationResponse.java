package com.richstone.cargo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.richstone.cargo.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    @JsonProperty("access_token")
    @Schema(description = "Токен доступа", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;
    @JsonProperty("refresh_token")
    @Schema(description = "Токен для обновления доступа", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String refreshToken;
    @JsonProperty("user")
    private DriverDto user;
}
