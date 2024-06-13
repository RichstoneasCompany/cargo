package com.richstone.cargo.mapper;

import com.richstone.cargo.dto.CargoDto;
import com.richstone.cargo.model.Cargo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CargoMapper {
    CargoMapper INSTANCE = Mappers.getMapper(CargoMapper.class);

    CargoDto cargoToCargoDto(Cargo cargo);

    Cargo dtoToCargo(CargoDto cargoDto);

}
