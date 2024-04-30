package com.example.richstonecargo.data.remote.dto

data class UserInfo(
    val id: Int,
    val name: String,
    val access_token: String,
    val surname: String
)


data class LoginUserState(
    val isLoading: Boolean = false,
    val userInfo: UserInfo? = null,
    val error: String = ""
)

