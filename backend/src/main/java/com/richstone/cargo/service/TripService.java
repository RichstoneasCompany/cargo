package com.richstone.cargo.service;

import com.richstone.cargo.dto.TripDto;
import com.richstone.cargo.model.Trip;

import java.time.LocalDate;
import java.util.List;

public interface TripService {
    List<Long> findTripIdsByDriverId(Long driverId, LocalDate start, LocalDate end);
    void addTrip(TripDto tripDto);
    List<Trip> getAllTrips();
    Trip findById(Long id);
    void save(Trip trip);
}
