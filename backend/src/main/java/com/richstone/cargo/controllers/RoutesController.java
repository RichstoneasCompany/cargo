package com.richstone.cargo.controllers;

import com.richstone.cargo.service.impl.DriverServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/routes")
@CrossOrigin("*")
@RequiredArgsConstructor
@Tag(name = "RoutesController", description = "API для управления маршрутами")
public class RoutesController {
    private final DriverServiceImpl driverService;

    @GetMapping("/{id}")
    @Operation(summary = "Получение маршрута",
            description = "Возвращает маршрут для указанного идентификатора")
    public String getRoute(@PathVariable Long id) {
        return driverService.buildRoute(id);
    }
}
