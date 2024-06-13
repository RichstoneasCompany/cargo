package com.example.richstonecargo.domain.model

data class ForgotPasswordVerifyOtpResult(
    val access_token: String
)

data class ForgotPasswordVerifyOtpState(
    val isLoading: Boolean = false,
    val forgotPasswordVerifyOtpResult: ForgotPasswordVerifyOtpResult? = null,
    val error: String = ""
)
