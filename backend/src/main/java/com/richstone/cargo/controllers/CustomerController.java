package com.richstone.cargo.controllers;

import com.richstone.cargo.dto.AuthenticationResponse;
import com.richstone.cargo.dto.UserDto;
import com.richstone.cargo.service.impl.CustomerServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
@Tag(name = "CustomerController", description = "API для управления данными клиентов")
public class CustomerController {

    private final CustomerServiceImpl customerService;

    @PostMapping()
    @Operation(summary = "Регистрация нового пользователя",
            description = "Регистрация пользователя и создание аккаунта")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody UserDto dto) {
        return ResponseEntity.ok(customerService.registerCustomer(dto));
    }
}
