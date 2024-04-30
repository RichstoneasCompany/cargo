package com.richstone.cargo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TruckDto {
    private String model;
    private String numberPlate;
    private Integer maxPermMass;
}
