package com.richstone.cargo.service.impl;

import com.richstone.cargo.dto.AvailableTripDto;
import com.richstone.cargo.dto.TripDto;
import com.richstone.cargo.exception.TripNotFoundException;
import com.richstone.cargo.mapper.TripMapper;
import com.richstone.cargo.model.*;
import com.richstone.cargo.model.types.TripStatus;
import com.richstone.cargo.repository.DriverRepository;
import com.richstone.cargo.repository.RouteRepository;
import com.richstone.cargo.repository.TripRepository;
import com.richstone.cargo.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {
    private final TripRepository tripRepository;
    private final DriverRepository driverRepository;
    private final RouteRepository routeRepository;
    private final CargoServiceImpl cargoService;

    @Override
    public List<Long> findTripIdsByDriverId(Long driverId, LocalDate start, LocalDate end) {
        log.info("Finding trip IDs by driverId: {} for the period from {} to {}", driverId, start, end);

        List<Long> tripIds = tripRepository.findTripIdsByDriverIdAndPeriod(driverId, start.atStartOfDay(), end.atTime(23, 59, 59))
                .orElseThrow(() -> {
                    log.error("No trip IDs found for the specified driverId: {} for the period from {} to {}", driverId, start, end);
                    return new TripNotFoundException("No trip IDs found for the specified driverId: " + driverId, new TripNotFoundException());
                });

        log.info("Trip IDs found {} ", tripIds);
        return tripIds;
    }

    public Trip findTripByAssignedDriver(Driver driver) {
        log.info("Finding tripId by assigned driver : {}", driver);
        Trip trip = tripRepository.findTripByAssignedDriver(driver).orElseThrow(() ->
                new TripNotFoundException("No trip found for this driver {}" + driver, new TripNotFoundException()));
        log.info("Trip found");

        return trip;
    }

    @Override
    public void addTrip(TripDto tripDto) {
        log.info("Adding new trip:");

        Optional<Route> route = routeRepository.findById(tripDto.getRouteId());
        Optional<Driver> driver = driverRepository.findByUserId(tripDto.getAssignedDriverId());

        Trip trip = Trip.builder()
                .route(route.get())
                .arrivalTime(tripDto.getArrivalTime())
                .departureTime(tripDto.getDepartureTime())
                .assignedDriver(driver.get())
                .tripStatus(TripStatus.NEW)
                .tripNumber(generateTripNumber())
                .build();

        Trip savedTrip = tripRepository.save(trip);
        log.info("New trip added successfully");
        Cargo cargo = cargoService.save(tripDto, trip);
        savedTrip.setCargo(cargo);
        tripRepository.save(savedTrip);
    }

    @Override
    public List<Trip> getAllTrips() {
        log.info("Fetching all trips from the database");
        List<Trip> trips = tripRepository.findAll();
        log.info("Retrieved {} trips from the database", trips.size());
        return trips;
    }

    @Override
    public Trip findById(Long id) {
        log.info("Finding trip by id: {}", id);
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Trip not found with this id: {}", id);
                    return new TripNotFoundException("Trip not found with this id: " + id);
                });
        log.info("Trip found with id:  {}", id);
        return trip;
    }

    @Override
    public void save(Trip trip) {
        log.info("Saving trip");
        tripRepository.save(trip);
        log.info("Trip saved successfully");
    }

    public List<Trip> getActiveTrips() {
        log.info("Fetching active trips from the database");
        List<Trip> trips = tripRepository.findAllByTripStatus(TripStatus.IN_PROGRESS);
        log.info("Retrieved {} trips from the database", trips.size());
        return trips;
    }

    public List<Trip> getTripsHistory() {
        log.info("Fetching active trips from the database");
        List<Trip> trips = tripRepository.findAllByTripStatusIn(List.of(TripStatus.FINISHED, TripStatus.CANCELLED));
        log.info("Retrieved {} trips from the database", trips.size());
        return trips;
    }

    public void cancelTrip(Long id) {
        log.info("Cancelling trip: {}", id);
        Trip trip = findById(id);
        trip.setTripStatus(TripStatus.CANCELLED);
        trip.setDeleted(true);
        tripRepository.save(trip);
    }

    public List<AvailableTripDto> getAvailableTrips() {
        log.info("Fetching available trips from the database");
        List<Trip> trips = tripRepository.findAllByTripStatus(TripStatus.NEW);
        log.info("Retrieved {} trips from the database", trips.size());

        return trips.stream()
                .map(trip -> {
                    AvailableTripDto dto = TripMapper.INSTANCE.toDto(trip);
                    dto.setStartLocation(trip.getRoute().getStartLocationId().getName());
                    dto.setEndLocation(trip.getRoute().getEndLocationId().getName());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public static String generateTripNumber() {
        final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String DIGITS = "0123456789";
        final Random RANDOM = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            int randomIndex = RANDOM.nextInt(LETTERS.length());
            char randomChar = LETTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        sb.append("-");

        for (int i = 0; i < 3; i++) {
            int randomIndex = RANDOM.nextInt(DIGITS.length());
            char randomDigit = DIGITS.charAt(randomIndex);
            sb.append(randomDigit);
        }

        return sb.toString();
    }

    public AvailableTripDto getTripById(Long id) {
        Trip trip = findById(id);
        AvailableTripDto dto = TripMapper.INSTANCE.toDto(trip);
        dto.setStartLocation(trip.getRoute().getStartLocationId().getName());
        dto.setEndLocation(trip.getRoute().getEndLocationId().getName());
        return dto;
    }
}
