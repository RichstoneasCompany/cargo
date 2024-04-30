package com.richstone.cargo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayslipResponseDto implements Serializable {
    @Schema(description = "Идентификатор ведомости", example = "456")
    private Long id;

    @Schema(description = "ID водителя", example = "123")
    private Long driver;

    @Schema(description = "Начало периода", example = "2023-01-01")
    private LocalDate periodStart;

    @Schema(description = "Конец периода", example = "2023-01-31")
    private LocalDate periodEnd;

    @Schema(description = "Список доходов")
    private List<IncomeDto> incomes;

    @Schema(description = "Список расходов")
    private List<ExpenseDto> expenses;

}
