package com.example.richstonecargo.data.remote.dto

data class UserDetailsDto(
    val username: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val phone: String,
    val gender: String,
    val birthDate: String?,
)
