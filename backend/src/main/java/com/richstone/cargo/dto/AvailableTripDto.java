package com.richstone.cargo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableTripDto {
    @Schema(description = "Номер поездки")
    private String tripNumber;
    @Schema(description = "Время отправления")
    private LocalDateTime departureTime;
    @Schema(description = "Время прибытия")
    private LocalDateTime arrivalTime;
    @Schema(description = "Место отправления")
    private String startLocation;
    @Schema(description = "Место прибытия")
    private String endLocation;
}
