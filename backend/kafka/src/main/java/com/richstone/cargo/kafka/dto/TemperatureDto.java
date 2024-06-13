package com.richstone.cargo.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemperatureDto {
    private Long userId;
    private double temperature;
    private LocalDateTime timestamp;
}
