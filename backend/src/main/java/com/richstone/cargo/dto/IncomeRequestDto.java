package com.richstone.cargo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomeRequestDto {
    @Schema(description = "Идентификатор поездки")
    private Long tripId;
    @Schema(description = "Сумма дохода")
    private Double amount;
    @Schema(description = "Описание дохода")
    private String description;
}
