package com.richstone.cargo.controllers;

import com.richstone.cargo.dto.CargoDto;
import com.richstone.cargo.service.impl.CargoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/cargo")
@RequiredArgsConstructor
@Tag(name = "CargoController", description = "API для управления грузом")
public class CargoController {
    private final CargoServiceImpl cargoService;

    @Operation(summary = "Получение груза по идентификатору поездки",
            description = "Метод позволяет получить информацию о грузе, связанном с указанной поездкой.")
    @GetMapping("/trip/{id}")
    public ResponseEntity<CargoDto> getCargoByTripId(@PathVariable("id") Long id){
        CargoDto cargoByTripId = cargoService.getCargoByTripId(id);
        return ResponseEntity.ok(cargoByTripId);
    }

}
