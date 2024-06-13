package com.richstone.cargo.dto;

import com.richstone.cargo.model.types.ExpenseType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequestDto {
    @Schema(description = "Идентификатор поездки")
    private Long tripId;
    @Schema(description = "Сумма расхода")
    private Double amount;
    @Schema(description = "Описание расхода")
    private String description;
    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;
}
