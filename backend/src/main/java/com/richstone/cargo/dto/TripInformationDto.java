package com.richstone.cargo.dto;

import com.richstone.cargo.model.types.TripStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripInformationDto {
    @Schema(description = "Идентификатор поездки")
    private Long id;
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
    @Schema(description = "Расстояние между начальным и конечным местоположением")
    private String distance;
    @Schema(description = "Продолжительность поездки")
    private String duration;
    @Schema(description = "Статус поездки")
    @Enumerated(EnumType.STRING)
    private TripStatus tripStatus;
}
