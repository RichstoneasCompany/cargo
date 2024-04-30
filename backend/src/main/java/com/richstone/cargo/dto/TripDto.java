package com.richstone.cargo.dto;

import com.richstone.cargo.model.Trip;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripDto {
    private Long routeId;
    private String tripNumber;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Long assignedDriverId;
    private String tripStatus;
    private String cargoName;
    private String cargoDescription;
    private Double cargoWeight;
    private Double cargoVolume;
    private Integer numberOfPallets;
    private Double temperature;
}
