package com.richstone.cargo.dto;

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
    private String tripNumber;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String startLocation;
    private String endLocation;
}
