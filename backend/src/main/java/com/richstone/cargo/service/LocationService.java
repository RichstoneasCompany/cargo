package com.richstone.cargo.service;

import com.richstone.cargo.dto.LocationDto;
import com.richstone.cargo.model.Location;

import java.util.List;

public interface LocationService {
    void saveLocation(LocationDto locationDto);
    List<Location> getAllLocation();
    Location findById(Long id);
    void delete(Location location);
}
