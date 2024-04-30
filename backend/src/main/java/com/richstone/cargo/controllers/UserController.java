package com.richstone.cargo.controllers;

import com.richstone.cargo.dto.ChangePasswordDto;
import com.richstone.cargo.dto.UserDetailsDto;
import com.richstone.cargo.model.Image;
import com.richstone.cargo.service.impl.AuthenticationServiceImpl;
import com.richstone.cargo.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "UserController", description = "API для управления пользователями")
public class UserController {

    private final AuthenticationServiceImpl authenticationService;
    private final UserServiceImpl userService;


    @PutMapping("/password")
    @Operation(summary = "Изменение пароля",
            description = "Позволяет пользователю изменить свой пароль")
    public ResponseEntity<?> changePassword(@RequestParam("newPassword") String newPassword,
                                            @RequestParam("confirmPassword") String confirmPassword,
                                            Principal connectedUser) {
        authenticationService.changePassword(newPassword, confirmPassword, connectedUser);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Operation(summary = "Обновление данных пользователя",
            description = "Обновляет информацию о пользователе")
    public ResponseEntity<UserDetailsDto> updateUser(@RequestBody UserDetailsDto userDto, Principal principal) {
        UserDetailsDto userUpdated = userService.updateUser(userDto, principal);
        return ResponseEntity.ok(userUpdated);
    }

    @GetMapping
    @Operation(summary = "Получение данных пользователя",
            description = "Получает информацию о пользователе")
    public UserDetailsDto getUserDto() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsDto userDetailsDto = userService.getUserDtoByPrincipal(authentication);
        return userDetailsDto;
    }
}