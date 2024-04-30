package com.richstone.cargo.controllers;

import com.richstone.cargo.dto.*;
import com.richstone.cargo.event.RegistrationCompleteEvent;
import com.richstone.cargo.model.User;
import com.richstone.cargo.model.VerificationToken;
import com.richstone.cargo.repository.VerificationTokenRepository;
import com.richstone.cargo.service.impl.AuthenticationServiceImpl;
import com.richstone.cargo.service.impl.DispatcherServiceImpl;
import com.richstone.cargo.service.impl.DriverServiceImpl;
import com.richstone.cargo.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/v1/driver")
@Tag(name = "DriverController", description = "API для управления данными водителей")
public class DriverController {
    private final DriverServiceImpl driverService;
    private final DispatcherServiceImpl dispatcherService;
    private final ApplicationEventPublisher publisher;
    private final AuthenticationServiceImpl authenticationServiceImpl;
    private final VerificationTokenRepository tokenRepository;

    @PostMapping
    @Operation(summary = "Регистрация водителя",
            description = "Создание аккаунта водителя")
    public ResponseEntity<Object> registerDriver(@RequestBody RegistrationDto userDto) {
        driverService.registerDriver(userDto);
        return ResponseEntity.status(HttpStatus.OK).body("{\"result\": \"success\"}");
    }
    @PreAuthorize("hasAuthority('dispatcher:create')")
    @PostMapping("/verify")
    @Operation(summary = "Верификация водителя",
            description = "Проверка данных водителя",
            security = @SecurityRequirement(name = "bearerAuth"))
    public String verifyUser(@RequestBody VerifyDto dto, final HttpServletRequest request) {
        User user = dispatcherService.verifyDriver(dto);
        publisher.publishEvent(new RegistrationCompleteEvent(user, authenticationServiceImpl.applicationUrl(request)));
        return "Driver verified successfully!";
    }

    @GetMapping("/confirmEmail")
    @Operation(summary = "Подтверждение Email",
            description = "Проверка токена подтверждения Email")
    public String verifyEmail(@RequestParam("token") String token) {
        VerificationToken theToken = tokenRepository.findByToken(token);
        if (theToken.getUser().isEnabled()) {
            return "This account has already been verified, please, login.";
        }
        String verificationResult = driverService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid")) {
            return "Email verified successfully. Now you can login to your account";
        }
        return "Invalid verification token";
    }

    @PostMapping("/login")
    @Operation(summary = "Аутентификация водителя",
            description = "Логин и получение токенов доступа и обновления")
    public AuthenticationResponse authenticateDriver(
            @RequestBody DriverLoginDto request
    ) {
        return driverService.loginDriver(request);
    }

}
