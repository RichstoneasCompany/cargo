package com.richstone.cargo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TruckRequestDto {
    @Schema(description = "Модель грузовика")
    private String model;
    @Schema(description = "Номерной знак грузовика")
    private String numberPlate;
    @Schema(description = "Разрешенная максимальная масса")
    private Integer maxPermMass;
    @Schema(description = "Масса грузовика")
    private Integer mass;
    @Schema(description = "Нагрузка на ось грузовика")
    private Double axleLoad;
    @Schema(description = "Высота грузовика")
    private Double height;
    @Schema(description = "Ширина грузовика")
    private Double width;
    @Schema(description = "Длина грузовика")
    private Integer length;
}
