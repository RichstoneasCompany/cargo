package com.example.richstonecargo.domain.model

import java.io.Serializable

data class CargoInfo(
    val id: String,
    val name: String,
    val description: String,
    val weight: Int,
    val numberOfPallets: Int,
    val temperature: String
) : Serializable
