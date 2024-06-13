package com.richstone.cargo.service.impl;

import com.google.api.gax.rpc.ApiException;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.Duration;
import com.google.maps.model.TravelMode;
import com.richstone.cargo.dto.CargoDto;
import com.richstone.cargo.dto.TripDetailsDto;
import com.richstone.cargo.dto.TripInformationDto;
import com.richstone.cargo.dto.TripDto;
import com.richstone.cargo.exception.*;
import com.richstone.cargo.mapper.TripMapper;
import com.richstone.cargo.model.*;
import com.richstone.cargo.model.types.TripStatus;
import com.richstone.cargo.repository.DriverRepository;
import com.richstone.cargo.repository.RouteRepository;
import com.richstone.cargo.repository.TripRepository;
import com.richstone.cargo.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final DriverRepository driverRepository;
    private final RouteRepository routeRepository;
    private final CargoServiceImpl cargoService;
    @Value("${api.google.key}")
    private String API_GOOGLE_MAPS;
    @Lazy
    @Autowired
    private DriverServiceImpl driverService;
    private final UserServiceImpl userService;
    private final IncomeServiceImpl incomeService;
    private final ExpenseServiceImpl expenseService;

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
        incomeService.addIncome(tripDto, trip);
        expenseService.addExpense(tripDto, trip);

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

    public List<TripInformationDto> getAllTrips(Driver driver) {
        log.info("Fetching available trips from the database");
        List<Trip> trips = tripRepository.findAllByAssignedDriverOrderByDepartureTimeAsc(driver).get();
        log.info("Retrieved {} trips from the database", trips.size());

        return trips.parallelStream()
                .map(trip -> {
                    // Преобразование Trip в DTO
                    TripInformationDto dto = TripMapper.INSTANCE.toDto(trip);

                    // Установка начального и конечного местоположения для DTO
                    dto.setStartLocation(trip.getRoute().getStartLocationId().getName());
                    dto.setEndLocation(trip.getRoute().getEndLocationId().getName());

                    // Рассчет расстояния и продолжительности поездки и установка значений в DTO
                    String[] str = calculateDistanceAndDuration(trip.getRoute().getStartLocationId().getCoordinates().getLatitude(),
                            trip.getRoute().getStartLocationId().getCoordinates().getLongitude(),
                            trip.getRoute().getEndLocationId().getCoordinates().getLatitude(),
                            trip.getRoute().getEndLocationId().getCoordinates().getLongitude());

                    dto.setDistance(str[0]);
                    dto.setDuration(str[1]);
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

    public TripDetailsDto getTripById(Long id) {
        log.info("Fetching trip details for trip ID: {}", id);
        Trip trip = findById(id);
        CargoDto cargo = null;
        try {
            cargo = cargoService.getCargoByTripId(id);
        } catch (CargoNotFoundException e) {
            log.warn("No cargo found for trip ID: {}", id);
        }
        TripInformationDto dto = TripMapper.INSTANCE.toDto(trip);
        dto.setStartLocation(trip.getRoute().getStartLocationId().getName());
        dto.setEndLocation(trip.getRoute().getEndLocationId().getName());

        String[] str = calculateDistanceAndDuration(trip.getRoute().getStartLocationId().getCoordinates().getLatitude(),
                trip.getRoute().getStartLocationId().getCoordinates().getLongitude(),
                trip.getRoute().getEndLocationId().getCoordinates().getLatitude(),
                trip.getRoute().getEndLocationId().getCoordinates().getLongitude());

        dto.setDistance(str[0]);
        dto.setDuration(str[1]);

        TripDetailsDto tripDetailsDto = new TripDetailsDto();
        tripDetailsDto.setTripInformation(dto);
        tripDetailsDto.setCargo(cargo);
        log.info("Returning trip details");
        return tripDetailsDto;
    }

    public Trip findInProgressTripByAssignedDriver(Driver driver) {
        return tripRepository.findByAssignedDriverAndTripStatus(driver, TripStatus.IN_PROGRESS)
                .orElseThrow(() -> {
                    log.error("Trip not found with this status: {}", TripStatus.IN_PROGRESS);
                    return new TripNotFoundException("Trip not found with this status: " + TripStatus.IN_PROGRESS);
                });
    }

    public void startTrip(Long driverId, Long tripId) {
        Optional<Driver> optionalDriver = driverRepository.findById(driverId);

        if (optionalDriver.isPresent()) {
            Driver driver = optionalDriver.get();
            log.info("Driver found: {}", driver.getUser().getId());

            Optional<Trip> currentInProgressTrip = tripRepository.findByAssignedDriverAndTripStatus(driver, TripStatus.IN_PROGRESS);

            if (currentInProgressTrip.isEmpty()) {
                log.info("No trip in progress for driver: {}", driver.getId());
                Optional<Trip> optionalTrip = tripRepository.findById(tripId);
                if (optionalTrip.isPresent()) {
                    Trip trip = optionalTrip.get();
                    if (trip.getAssignedDriver().equals(driver) && trip.getTripStatus() == TripStatus.NEW) {
                        trip.setTripStatus(TripStatus.IN_PROGRESS);
                        tripRepository.save(trip);
                        log.info("Trip started successfully");
                    } else {
                        log.error("Trip cannot be started. It is already in progress or finished, or not assigned to the driver.");
                        throw new TripInProgressException("Trip cannot be started. It is already in progress or finished, or not assigned to the driver.");
                    }
                }
            } else {
                throw new TripAlreadyExistsException("The driver has already started a trip");
            }
        } else {
            log.error("Driver not found.");
            throw new DriverNotFoundException("Driver not found");
        }
    }

    public String[] calculateDistanceAndDuration(double originLat, double originLng, double destLat, double destLng) {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(API_GOOGLE_MAPS)
                .build();

        try {
            DirectionsResult result = DirectionsApi.newRequest(context)
                    .origin(Double.toString(originLat) + "," + Double.toString(originLng))
                    .destination(Double.toString(destLat) + "," + Double.toString(destLng))
                    .mode(TravelMode.DRIVING)
                    .await();

            if (result.routes.length > 0 && result.routes[0].legs.length > 0) {
                String distance = result.routes[0].legs[0].distance.humanReadable;
                Duration duration = result.routes[0].legs[0].duration;

                return new String[]{distance, duration.toString()};
            } else {
                return new String[]{"No routes found", "N/A"};
            }
        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
            return new String[]{"Error", e.getMessage()};
        } catch (com.google.maps.errors.ApiException e) {
            throw new RuntimeException(e);
        }
    }

    public Long finishTrip(Long driverId, Long tripId) {
        Optional<Driver> optionalDriver = driverRepository.findById(driverId);

        if (optionalDriver.isPresent()) {
            Driver driver = optionalDriver.get();
            log.info("Driver found: {}", driver.getUser().getId());

            Optional<Trip> optionalTrip = tripRepository.findById(tripId);
            if (optionalTrip.isPresent()) {
                Trip trip = optionalTrip.get();
                if (trip.getAssignedDriver().equals(driver) && trip.getTripStatus() == TripStatus.IN_PROGRESS) {
                    trip.setTripStatus(TripStatus.FINISHED);
                    tripRepository.save(trip);
                    log.info("Trip finished successfully");
                    return tripId;
                } else {
                    log.error("Trip cannot be finished. It is not in progress or not assigned to the driver.");
                    throw new TripNotInProgressException("Trip is not in progress or not assigned to the driver");
                }
            } else {
                log.error("Trip not found.");
                throw new TripNotFoundException("Trip not found");
            }
        } else {
            log.error("Driver not found.");
            throw new DriverNotFoundException("Driver not found");
        }
    }
    public TripDetailsDto getCurrentTrip(Principal principal) {

        User user = userService.findByUsername(principal.getName());
        Driver driver = driverService.findByUserId(user.getId());
        Trip tripByAssignedDriver = findInProgressTripByAssignedDriver(driver);
        CargoDto cargo = cargoService.getCargoByTripId(tripByAssignedDriver.getId());

        log.info("Fetching current trip for driver {}", driver.getId());
        Trip trip = tripRepository.findByAssignedDriverAndTripStatus(driver, TripStatus.IN_PROGRESS).orElseThrow(
                () -> new TripNotFoundException("No trip in progress for driver " + driver.getId())
        );
        TripInformationDto dto = TripMapper.INSTANCE.toDto(trip);
        dto.setStartLocation(trip.getRoute().getStartLocationId().getName());
        dto.setEndLocation(trip.getRoute().getEndLocationId().getName());

        String[] str = calculateDistanceAndDuration(trip.getRoute().getStartLocationId().getCoordinates().getLatitude(),
                trip.getRoute().getStartLocationId().getCoordinates().getLongitude(),
                trip.getRoute().getEndLocationId().getCoordinates().getLatitude(),
                trip.getRoute().getEndLocationId().getCoordinates().getLongitude());

        dto.setDistance(str[0]);
        dto.setDuration(str[1]);

        TripDetailsDto tripDetailsDto = new TripDetailsDto();
        tripDetailsDto.setTripInformation(dto);
        tripDetailsDto.setCargo(cargo);
        log.info("Current trip found for driver {}: {}", driver.getId(), trip.getId());
        return tripDetailsDto;
    }

    public Page<Trip> getAllTrips(int pageNo, int pageSize) {
        log.info("Fetching trips for page number: {} with page size: {}", pageNo, pageSize);
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Trip> trips = tripRepository.findAll(pageable);
        log.info("Retrieved {} trips", trips.getNumberOfElements());
        return trips;
    }

    public Page<Trip> getActiveTrips(int pageNo, int pageSize) {
        log.info("Fetching active trips for page number: {} with page size: {}", pageNo, pageSize);
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Trip> trips = tripRepository.findAllByTripStatus(TripStatus.IN_PROGRESS, pageable);
        log.info("Retrieved {} active trips from the database", trips.getNumberOfElements());
        return trips;
    }

    public Page<Trip> getTripsHistory(int pageNo, int pageSize) {
        log.info("Fetching trips history for page number: {} with page size: {}", pageNo, pageSize);
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Trip> trips = tripRepository.findAllByTripStatusIn(List.of(TripStatus.FINISHED, TripStatus.CANCELLED), pageable);
        log.info("Retrieved {} trips from the database", trips.getNumberOfElements());
        return trips;
    }

    public void delete(Long id) {
        log.info("Deleting trip: {}", id);
        Trip trip = findById(id);
        tripRepository.delete(trip);
        log.info("Trip deleted successfully: {}", id);
    }

    public void deleteAllTripsByDriver(Driver driver) {
        log.info("Deleting all trips ");
        tripRepository.deleteAllByAssignedDriver(driver.getId());
        log.info("Trip deleted successfully ");
    }

    public List<Trip> findTripsByDriverId(Long driverId, LocalDate start, LocalDate end) {
        log.info("Finding trips by driverId: {} for the period from {} to {}", driverId, start, end);

        List<Trip> trips = tripRepository.findTripsByDriverIdAndPeriod(driverId, start.atStartOfDay(), end.atTime(23, 59, 59))
                .orElseThrow(() -> {
                    log.error("No trips found for the specified driverId: {} for the period from {} to {}", driverId, start, end);
                    return new TripNotFoundException("No trips found for the specified driverId: " + driverId, new TripNotFoundException());
                });
        log.info("Trips found {} ", trips);
        return trips;
    }

    public List<YearMonth> getMonthsWithTrips(Principal principal) {
        log.info("Getting months with trips for principal: {}", principal.getName());
        User user = userService.getUserByPrincipal(principal);
        Driver driver = driverService.findByUserId(user.getId());
        List<Trip> trips = tripRepository.findAllByAssignedDriver(driver);
        List<YearMonth> monthsWithTrips = trips.stream()
                .map(trip -> YearMonth.from(trip.getDepartureTime()))
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        log.info("Months with trips for principal {}: {}", principal.getName(), monthsWithTrips);
        return monthsWithTrips;
    }
    public List<Long> getAllTripIdsByDriver(Long driverId) {
        log.info("Getting all trip IDs for driver ID: {}", driverId);
        List<Long> tripIds = tripRepository.findTripIdsByDriverId(driverId);
        log.debug("Retrieved trip IDs: {}", tripIds);
        return tripIds;
    }


}
