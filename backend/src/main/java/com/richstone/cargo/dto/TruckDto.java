package com.richstone.cargo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TruckDto {
    @Schema(description = "Модель грузовика")
    private String model;
    @Schema(description = "Номерной знак грузовика")
    private String numberPlate;
    @Schema(description = "Максимальная разрешенная масса грузовика")
    private Integer maxPermMass;
}
