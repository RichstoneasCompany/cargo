package com.example.richstonecargo.data.remote.dto

import com.example.richstonecargo.domain.model.SalaryInfo

data class SalaryInfoDto(
    val baseSalary: Int,
    val bonuses: Int,
    val fine: Int,
    val incomeTax: Int,
    val totalPay: Int
)

fun SalaryInfoDto.toSalaryInfo(): SalaryInfo {
    return SalaryInfo(
        baseSalary = baseSalary,
        bonuses = bonuses,
        fine = fine,
        incomeTax = incomeTax,
        totalPay = totalPay
    )
}
