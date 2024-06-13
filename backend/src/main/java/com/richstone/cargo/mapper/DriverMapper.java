package com.richstone.cargo.mapper;

import com.richstone.cargo.dto.DriverUpdateDto;
import com.richstone.cargo.model.Driver;
import com.richstone.cargo.model.Truck;
import com.richstone.cargo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DriverMapper {
    DriverMapper INSTANCE = Mappers.getMapper(DriverMapper.class);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "truck", ignore = true)
    @Mapping(target = "licenseNumber", source = "driver.licenseNumber")
    void updateDriverFromDto(DriverUpdateDto driverUpdateDto, @MappingTarget Driver driver);

    @Mapping(target = "mass", ignore = true)
    @Mapping(target = "axleLoad", ignore = true)
    @Mapping(target = "height", ignore = true)
    @Mapping(target = "width", ignore = true)
    @Mapping(target = "length", ignore = true)
    @Mapping(target = "driver", ignore = true)
    @Mapping(target = "model", source = "truck.model")
    @Mapping(target = "numberPlate", source = "truck.numberPlate")
    @Mapping(target = "maxPermMass", source = "truck.maxPermMass")
    void updateTruckFromDto(DriverUpdateDto driverUpdateDto, @MappingTarget Truck truck);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "tokens", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "firstname", source = "user.firstname")
    @Mapping(target = "lastname", source = "user.lastname")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "phone", source = "user.phone")
    @Mapping(target = "role", source = "user.role")
    @Mapping(target = "enabled", source = "user.enabled")
    @Mapping(target = "gender", source = "user.gender")
    @Mapping(target = "birthDate", source = "user.birthDate")
    void updateUserFromDto(DriverUpdateDto driverUpdateDto, @MappingTarget User user);
}