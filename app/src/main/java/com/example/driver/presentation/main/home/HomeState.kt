package com.example.driver.presentation.main.home

import com.example.driver.domain.model.UserExtended
import com.example.driver.util.UiText

data class HomeState(
    val user: UserExtended? = null,
    val isUserActive: Boolean = false,
    val isLoading: Boolean = false,
    val error: UiText? = null
)