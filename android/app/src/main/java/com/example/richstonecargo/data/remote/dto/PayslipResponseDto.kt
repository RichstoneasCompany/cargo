package com.example.richstonecargo.data.remote.dto

data class PayslipResponseDto(
    val id: Long,
    val driver: Long,
    val periodStart: String,
    val periodEnd: String,
    val incomes: List<IncomeDto>,
    val expenses: List<ExpenseDto>
)
