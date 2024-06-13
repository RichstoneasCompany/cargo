package com.richstone.cargo.controllers;

import com.richstone.cargo.dto.RouteResponse;
import com.richstone.cargo.exception.TripNotFoundException;
import com.richstone.cargo.model.User;
import com.richstone.cargo.service.impl.DriverServiceImpl;
import com.richstone.cargo.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/routes")
@CrossOrigin("*")
@RequiredArgsConstructor
@Tag(name = "RoutesController", description = "API для управления маршрутами")
public class RoutesController {
    private final DriverServiceImpl driverService;
    private final UserServiceImpl userService;

    @GetMapping
    @Operation(summary = "Получение маршрута",
            description = "Возвращает маршрут для указанного идентификатора")
    public ResponseEntity<?> getRoute(Principal principal) {
        try {
            User user = userService.getUserByPrincipal(principal);
            RouteResponse routeResponse = driverService.buildRoute(user.getId());
            return new ResponseEntity<>(routeResponse, HttpStatus.OK);
        } catch (TripNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You don't have a current trip.");
        }
    }
}
