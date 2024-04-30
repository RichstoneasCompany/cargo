package com.richstone.cargo.controllers;

import com.richstone.cargo.dto.UserDto;
import com.richstone.cargo.service.impl.AdminServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@Tag(name = "AdminController", description = "API для администраторов")
public class AdminController {

    private final AdminServiceImpl adminServiceImpl;

    @GetMapping
    @Operation(summary = "Приветственное сообщение администратора")
    public String helloAdminController() {
        return "Admin level access";
    }

    @PostMapping
    @Operation(summary = "Регистрация нового администратора")
    public String registerAdmin(@RequestBody UserDto userDto) {
        adminServiceImpl.registerAdmin(userDto);
        return "Admin account created! ";
    }


}
