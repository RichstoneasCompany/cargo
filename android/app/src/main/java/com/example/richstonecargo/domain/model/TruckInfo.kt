package com.example.richstonecargo.domain.model

import java.io.Serializable

data class TruckInfo(
    val model: String,
    val numberPlate: String,
    val maxPermMass: String,
) : Serializable
