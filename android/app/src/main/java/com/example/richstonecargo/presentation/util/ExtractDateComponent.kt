package com.example.richstonecargo.presentation.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun extractDateComponent(
    date: Date,
    component: DateComponent,
    locale: Locale = Locale.getDefault()
): String {
    if (date == null) return ""
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", locale)
    return try {
        val calendar = Calendar.getInstance(locale).apply {
            time = date
        }
        when (component) {
            DateComponent.DAY -> calendar.get(Calendar.DAY_OF_MONTH).toString()
            DateComponent.MONTH -> (calendar.get(Calendar.MONTH) + 1).toString()
            DateComponent.YEAR -> calendar.get(Calendar.YEAR).toString()
            DateComponent.MONTH_NAME -> getMonthName(calendar.get(Calendar.MONTH))
        }
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

fun getMonthName(month: Int): String {
    val months = arrayOf(
        "январь", "февраль", "март", "апрель", "май", "июнь",
        "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"
    )
    return months[month]
}

enum class DateComponent {
    DAY, MONTH, YEAR, MONTH_NAME
}
