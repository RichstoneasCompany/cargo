package com.richstone.cargo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayslipDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Schema(description = "ID водителя", example = "123")
    private Long driverId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Начало периода расчета", example = "2023-01-01")
    private LocalDate periodStart;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Конец периода расчета", example = "2023-01-31")
    private LocalDate periodEnd;
}