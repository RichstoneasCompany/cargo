package com.example.richstonecargo.data.remote.dto

data class UserInfo(
    val name: String,
    val access_token: String,
    val surname: String,
    var profilePicture: String? = null
)


data class LoginUserState(
    val isLoading: Boolean = false,
    val userInfo: UserInfo? = null,
    val error: String = ""
)

