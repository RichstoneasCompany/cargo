package com.richstone.cargo.controllers;


import com.richstone.cargo.dto.LoginDto;
import com.richstone.cargo.dto.AuthenticationResponse;
import com.richstone.cargo.service.impl.AuthenticationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("*")
@RequiredArgsConstructor
@Tag(name = "AuthenticationController", description = "API для аутентификации пользователей")
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationServiceImpl;


    @PostMapping("/login")
    @Operation(summary = "Аутентификация пользователя",
            description = "Логин и получение токенов доступа и обновления")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody LoginDto request
    ) {
        return ResponseEntity.ok(authenticationServiceImpl.authenticate(request));
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Обновление токена доступа",
            description = "Обновление токенов доступа с использованием refresh токена")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationServiceImpl.refreshToken(request, response);
    }
}   
