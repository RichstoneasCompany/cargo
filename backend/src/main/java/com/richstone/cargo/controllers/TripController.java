package com.richstone.cargo.controllers;

import com.richstone.cargo.dto.AvailableTripDto;
import com.richstone.cargo.dto.CargoDto;
import com.richstone.cargo.dto.TripDto;
import com.richstone.cargo.mapper.TripMapper;
import com.richstone.cargo.model.Trip;
import com.richstone.cargo.service.impl.TripServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/trips")
@RequiredArgsConstructor
@Tag(name = "TripController", description = "API для управления поездками")
public class TripController {
    private final TripServiceImpl tripService;

    @Operation(summary = "Получение всех доступных поездок",
            description = "Метод позволяет получить список всех доступных поездок.")
    @GetMapping
    public ResponseEntity<List<AvailableTripDto>> getAvailableTrips(){
        List<AvailableTripDto> availableTrips = tripService.getAvailableTrips();
        return ResponseEntity.ok(availableTrips);

    }
    @Operation(summary = "Получение информации о поездке по идентификатору",
            description = "Метод позволяет получить информацию о конкретной поездке по её идентификатору.")
    @GetMapping("/{id}")
    public ResponseEntity<AvailableTripDto> getTripById(@PathVariable("id") Long id){
        AvailableTripDto tripDto = tripService.getTripById(id);
        return ResponseEntity.ok(tripDto);
    }

}

