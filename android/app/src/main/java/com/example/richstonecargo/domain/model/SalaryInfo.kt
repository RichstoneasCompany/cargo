package com.example.richstonecargo.domain.model

data class SalaryInfo(
    val baseSalary: Int,
    val bonuses: Int,
    val fine: Int,
    val incomeTax: Int,
    val totalPay: Int
)

