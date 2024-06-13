package com.example.richstonecargo.data.remote.dto

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.richstonecargo.domain.model.PayslipDetails
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class PayslipDetailsDto(
    val departureTime: String,
    val arrivalTime: String,
    val baseSalary: Int
)

@RequiresApi(Build.VERSION_CODES.O)
fun PayslipDetailsDto.toPayslipDetails(): PayslipDetails {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    return PayslipDetails(
        departureTime = LocalDate.parse(departureTime, formatter),
        arrivalTime = LocalDate.parse(arrivalTime, formatter),
        baseSalary = baseSalary
    )
}
