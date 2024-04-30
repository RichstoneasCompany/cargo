package com.richstone.cargo.controllers;

import com.richstone.cargo.dto.UserDto;
import com.richstone.cargo.service.impl.AdminServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/v1/dispatcher")
@CrossOrigin("*")
@RequiredArgsConstructor
@Tag(name = "DispatcherController", description = "API для управления диспетчерами")
public class DispatcherController {
    private final AdminServiceImpl adminServiceImpl;

    @PostMapping()
    @PreAuthorize("hasAuthority('admin:create')")
    @Operation(summary = "Добавление нового диспетчера",
            description = "Добавляет нового диспетчера в систему",
            security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<String> addDispatcher(@RequestBody UserDto dto) {
        try {
            adminServiceImpl.addDispatcher(dto);
            return ResponseEntity.ok("Dispatcher added successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
