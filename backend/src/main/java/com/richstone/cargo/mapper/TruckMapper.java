package com.richstone.cargo.mapper;

import com.richstone.cargo.dto.TruckDto;
import com.richstone.cargo.dto.TruckParamsDto;
import com.richstone.cargo.dto.TruckRequestDto;
import com.richstone.cargo.model.Truck;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TruckMapper {
    TruckMapper INSTANCE = Mappers.getMapper(TruckMapper.class );

    Truck dtoToTruck(TruckRequestDto truckRequestDto);
    TruckDto truckToDto(Truck truck);

}
