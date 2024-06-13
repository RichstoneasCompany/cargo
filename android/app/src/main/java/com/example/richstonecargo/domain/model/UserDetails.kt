package com.example.richstonecargo.domain.model

import java.util.Date

enum class Gender(val displayName: String) {
    MALE("Мужской"),
    FEMALE("Женский")
}

data class UserDetails(
    val username: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val phone: String,
    val gender: Gender,
    val birthDate: Date?,
    var truckInfo: TruckInfo?
)
