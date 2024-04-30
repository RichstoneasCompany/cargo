package com.richstone.cargo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDto {
    private Long id;
    private Double amount;
    private String description;
    private LocalDate date;
}
