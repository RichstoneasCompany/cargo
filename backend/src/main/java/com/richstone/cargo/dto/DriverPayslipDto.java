package com.richstone.cargo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverPayslipDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Schema(description = "Оклад по рейсам")
    private double baseSalary;
    @Schema(description = "Премия")
    private double bonuses;
    @Schema(description = "Штраф")
    private double fine;
    @Schema(description = "НДФЛ")
    private double incomeTax ;
    @Schema(description = "Сумма к выплате")
    private double totalPay;
}
