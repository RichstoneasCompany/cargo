package com.example.richstonecargo.domain.model

data class SendOtpResult(
    val status: String,
    val message: String
)

data class ForgotPasswordSendOtpState(
    val isLoading: Boolean = false,
    val forgotPasswordSendOtpResult: SendOtpResult? = null,
    val error: String = ""
)
