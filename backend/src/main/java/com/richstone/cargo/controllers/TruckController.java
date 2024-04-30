package com.richstone.cargo.controllers;

import com.richstone.cargo.dto.TruckDto;
import com.richstone.cargo.model.Driver;
import com.richstone.cargo.model.User;
import com.richstone.cargo.service.impl.DriverServiceImpl;
import com.richstone.cargo.service.impl.TruckServiceImpl;
import com.richstone.cargo.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/truck")
@RequiredArgsConstructor
@Tag(name = "TruckController", description = "API для управления грузовиком")
public class TruckController {
    private final TruckServiceImpl truckService;
    private final DriverServiceImpl driverService;
    private final UserServiceImpl userService;

    @Operation(summary = "Получение грузовика водителя",
            description = "Возвращает данные о грузовике водителя")
    @GetMapping
    public ResponseEntity<TruckDto> getTruck(Principal principal){
        User user = userService.getUserByPrincipal(principal);
        Driver driver = driverService.findByUserId(user.getId());
        TruckDto truckDto = truckService.getTruckByDriver(driver);
        return ResponseEntity.ok(truckDto);
    }


}
