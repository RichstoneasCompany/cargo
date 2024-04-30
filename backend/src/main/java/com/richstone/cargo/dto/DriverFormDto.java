package com.richstone.cargo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverFormDto {
    private UserDto user;
    private TruckRequestDto truck;
    private String licenseNumber;
}
