package com.example.richstonecargo.presentation.profile

import com.example.richstonecargo.domain.model.UserDetails

data class ProfileDetailsState(
    val isLoading: Boolean = false,
    val profileDetails: UserDetails? = null,
    val error: String = ""
)
