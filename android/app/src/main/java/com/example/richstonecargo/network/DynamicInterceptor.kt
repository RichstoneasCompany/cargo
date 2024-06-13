package com.example.richstonecargo.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

object DynamicInterceptor : Interceptor {
    var token: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        token?.let {
            builder.addHeader("Authorization", "Bearer $it")
        }

        Log.d("DynamicInterceptor", "Set new token: $token")

        return chain.proceed(builder.build())
    }
}
