package com.richstone.cargo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayslipDetailsDto {
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Schema(description = "Время отправления рейса")
    private LocalDateTime departureTime;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Schema(description = "Время прибытия рейса")
    private LocalDateTime arrivalTime;
    @Schema(description = "Оклад по рейсам")
    private double baseSalary;
}
