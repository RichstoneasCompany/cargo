package com.richstone.cargo.dto;

import com.richstone.cargo.model.Trip;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripDto {
    @Schema(description = "Идентификатор маршрута")
    private Long routeId;
    @Schema(description = "Номер поездки")
    private String tripNumber;
    @Schema(description = "Время отправления")
    private LocalDateTime departureTime;
    @Schema(description = "Время прибытия")
    private LocalDateTime arrivalTime;
    @Schema(description = "Идентификатор назначенного водителя")
    private Long assignedDriverId;
    @Schema(description = "Статус поездки")
    private String tripStatus;
    @Schema(description = "Наименование груза")
    private String cargoName;
    @Schema(description = "Описание груза")
    private String cargoDescription;
    @Schema(description = "Вес груза в килограммах")
    private Double cargoWeight;
    @Schema(description = "Объем груза")
    private Double cargoVolume;
    @Schema(description = " Количество паллет груза")
    private Integer numberOfPallets;
    @Schema(description = "Температура хранения груза в градусах Цельсия")
    private Double temperature;
    @Schema(description = "Сумма дохода")
    private Double incomeAmount;
    @Schema(description = "Описание дохода")
    private String incomeDescription;

}
