package com.richstone.cargo.service;

import com.richstone.cargo.dto.CargoDto;
import com.richstone.cargo.dto.TripDto;
import com.richstone.cargo.model.Cargo;
import com.richstone.cargo.model.Trip;

public interface CargoService {
    Cargo save(TripDto tripDto, Trip trip);
    CargoDto getCargoByTripId(Long tripId);
}
