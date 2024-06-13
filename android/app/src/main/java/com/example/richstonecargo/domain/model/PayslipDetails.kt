package com.example.richstonecargo.domain.model

import java.time.LocalDate

data class PayslipDetails(
    val departureTime: LocalDate,
    val arrivalTime: LocalDate,
    val baseSalary: Int
)

