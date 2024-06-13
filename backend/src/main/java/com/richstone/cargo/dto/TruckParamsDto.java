package com.richstone.cargo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TruckParamsDto {
    @Schema(description = "Разрешенная максимальная масса")
    private double maxPermMass;
    @Schema(description = "Масса грузовика")
    private double mass;
    @Schema(description = "Нагрузка на ось грузовика")
    private double axleLoad;
    @Schema(description = "Высота грузовика")
    private double height;
    @Schema(description = "Ширина грузовика")
    private double width;
    @Schema(description = "Длина грузовика")
    private double length;
    @Schema(description = "Знак наличия опасного груза")
    private boolean dangerousCargo;
    @Schema(description = "Знак наличия взрывоопасного груза")
    private boolean explosiveCargo;
}