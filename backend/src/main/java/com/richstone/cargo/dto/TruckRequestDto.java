package com.richstone.cargo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TruckRequestDto {
    private String model;
    private String numberPlate;
    private Integer maxPermMass;
    private Integer mass;
    private Double axleLoad;
    private Double height;
    private Double width;
    private Integer length;
}
