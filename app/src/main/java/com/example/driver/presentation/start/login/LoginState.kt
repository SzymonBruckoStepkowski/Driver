package com.example.driver.presentation.start.login

import com.example.driver.util.UiText

data class LoginState(
    val isLoading: Boolean = false,
    val error: UiText? = null
)