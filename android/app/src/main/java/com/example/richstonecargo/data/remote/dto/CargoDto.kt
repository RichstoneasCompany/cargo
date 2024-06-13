package com.example.richstonecargo.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CargoDto(
    @SerializedName("name")
    val name: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("weight")
    val weight: Double?,

    @SerializedName("numberOfPallets")
    val numberOfPallets: Int?,

    @SerializedName("temperature")
    val temperature: Double?
)