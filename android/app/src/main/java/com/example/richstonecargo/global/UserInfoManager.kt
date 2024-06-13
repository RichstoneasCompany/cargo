package com.example.richstonecargo.global

import com.example.richstonecargo.data.remote.dto.UserInfo


object UserInfoManager {
    private var userInfo: UserInfo? = null
    private var phone: String? = null
    private var otpCode: String? = null
    private var deviceToken: String? = null

    fun setPhone(phone: String) {
        this.phone = phone
    }

    fun getPhone(): String? = phone

    fun setOtpCode(otpCode: String) {
        this.otpCode = otpCode
    }

    fun getOtpCode(): String? = otpCode

    fun setUserInfo(userInfo: UserInfo) {
        this.userInfo = userInfo
    }

    fun getUserInfo(): UserInfo? = userInfo

    fun clearUserInfo() {
        userInfo = null
    }

    fun setDeviceToken(token: String?) {
        deviceToken = token
    }

    fun getDeviceToken(): String? = deviceToken
}
