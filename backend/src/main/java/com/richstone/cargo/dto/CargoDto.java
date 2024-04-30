package com.richstone.cargo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CargoDto {
    private String name;
    private String description;
    private Double weight;
    private Integer numberOfPallets;
    private Double temperature;
}
