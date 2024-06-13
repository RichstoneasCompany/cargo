package com.richstone.cargo.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CargoDto {
    @Schema(description = "Наименование груза")
    private String name;
    @Schema(description = "Описание груза")
    private String description;
    @Schema(description = "Вес груза в килограммах.")
    private Double weight;
    @Schema(description = "Количество паллет груза")
    private Integer numberOfPallets;
    @Schema(description = "Температура хранения груза в градусах Цельсия")
    private Double temperature;
}
