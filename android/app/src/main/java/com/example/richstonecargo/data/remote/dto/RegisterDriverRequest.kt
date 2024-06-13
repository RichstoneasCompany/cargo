package com.example.richstonecargo.data.remote.dto

data class RegisterDriverRequest(
    val phone: String,
    val password: String,
    val deviceToken: String
)
