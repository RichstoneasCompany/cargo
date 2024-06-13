package com.richstone.cargo.controllers;

import com.richstone.cargo.dto.FinishedTripResponseDto;
import com.richstone.cargo.dto.TripChangeHistoryDto;
import com.richstone.cargo.dto.TripDetailsDto;
import com.richstone.cargo.dto.TripInformationDto;
import com.richstone.cargo.exception.TripInProgressException;
import com.richstone.cargo.exception.TripNotFoundException;
import com.richstone.cargo.exception.TripNotInProgressException;
import com.richstone.cargo.model.Driver;
import com.richstone.cargo.model.User;
import com.richstone.cargo.service.impl.DriverServiceImpl;
import com.richstone.cargo.service.impl.TripChangeHistoryServiceImpl;
import com.richstone.cargo.service.impl.TripServiceImpl;
import com.richstone.cargo.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.YearMonth;
import java.util.List;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/trips")
@RequiredArgsConstructor
@Tag(name = "TripController", description = "API для управления поездками")
public class TripController {
    private final TripServiceImpl tripService;
    private final DriverServiceImpl driverService;
    private final UserServiceImpl userService;
    private final TripChangeHistoryServiceImpl tripChangeHistoryService;

    @Operation(summary = "Получение всех поездок водителя",
            description = "Метод позволяет получить список всех поездок.")
    @GetMapping
    public ResponseEntity<List<TripInformationDto>> getAllTrips(Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Driver driver = driverService.findByUserId(user.getId());
        List<TripInformationDto> trips = tripService.getAllTrips(driver);
        return ResponseEntity.ok(trips);

    }

    @Operation(summary = "Получение информации о поездке по идентификатору",
            description = "Метод позволяет получить информацию о конкретной поездке по её идентификатору.")
    @GetMapping("/{id}")
    public ResponseEntity<?> getTripById(@PathVariable("id") Long id) {
        try {
            TripDetailsDto trip = tripService.getTripById(id);
            return ResponseEntity.ok(trip);
        }catch (TripNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @Operation(summary = "Начать поездку",
            description = "Начать поездку для водителя")
    @PostMapping("/start")
    public ResponseEntity<String> startTrip(Principal principal, @RequestParam Long tripId) {
        try {
        User user = userService.findByUsername(principal.getName());
        Driver driver = driverService.findByUserId(user.getId());
        tripService.startTrip(driver.getId(), tripId);
        return ResponseEntity.ok("Trip started successfully");
        }catch (TripInProgressException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Закончить поездку",
            description = "Закончить поездку для водителя")
    @PostMapping("/finish")
    public ResponseEntity<?> finishTrip(Principal principal, @RequestParam Long tripId) {
        try {
            User user = userService.findByUsername(principal.getName());
            Driver driver = driverService.findByUserId(user.getId());

            Long finishedTripId = tripService.finishTrip(driver.getId(), tripId);
            FinishedTripResponseDto response = new FinishedTripResponseDto(finishedTripId);
            return ResponseEntity.ok(response);
        }catch (TripNotInProgressException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Получение текущего рейса водителя",
            description = "Возвращает текущего рейса водителя")
    @GetMapping("/active")
    public ResponseEntity<?> getCurrentTrip(Principal principal) {
        try {
            TripDetailsDto currentTrip = tripService.getCurrentTrip(principal);
            return ResponseEntity.ok(currentTrip);
        } catch (TripNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You do not have an active trip.");
        }
    }

    @GetMapping("/months")
    @Operation(summary = "Получение всех месяцев, в которых были поездки",
            description = "Метод возвращает список всех месяцев, в которых происходили поездки")
    public ResponseEntity<List<YearMonth>> getMonthsWithTrips(Principal principal) {
        List<YearMonth> monthsWithTrips = tripService.getMonthsWithTrips(principal);
        return ResponseEntity.ok(monthsWithTrips);
    }

    @GetMapping("/changeHistory")
    @Operation(summary = "Получение истории изменений поездок",
            description = "Возвращает историю изменений поездок водителя.")
    public ResponseEntity<List<TripChangeHistoryDto>> getTripChangeHistory(Principal principal) {
        List<TripChangeHistoryDto> changeHistory = tripChangeHistoryService.getChangeHistoryForDriver(principal);
        return ResponseEntity.ok(changeHistory);
    }
}

