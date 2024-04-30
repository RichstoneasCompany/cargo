package com.richstone.cargo.mapper;

import com.richstone.cargo.dto.AvailableTripDto;
import com.richstone.cargo.dto.TripDto;
import com.richstone.cargo.model.Trip;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TripMapper {
    TripMapper INSTANCE = Mappers.getMapper(TripMapper.class);
    AvailableTripDto toDto(Trip trip);
    TripDto toTripDto(Trip trip);
}
