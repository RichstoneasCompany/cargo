package com.example.richstonecargo.data.remote.dto

data class ExpenseDto(
    val id: Long,
    val amount: Double,
    val description: String,
    val date: String
)