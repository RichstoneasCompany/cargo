package com.richstone.cargo.controllers;

import com.richstone.cargo.dto.TruckDto;
import com.richstone.cargo.service.impl.TruckServiceImpl;
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

    @Operation(summary = "Получение грузовика водителя",
            description = "Возвращает данные о грузовике водителя")
    @GetMapping
    public ResponseEntity<TruckDto> getTruck(Principal principal) {
        TruckDto truckDto = truckService.getTruckByPrincipal(principal);
        return ResponseEntity.ok(truckDto);
    }


}
