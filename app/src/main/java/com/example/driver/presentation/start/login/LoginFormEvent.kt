package com.example.driver.presentation.start.login

sealed class LoginFormEvent {
    data class OnLoginBtnClicked(val login: String, val password: String) : LoginFormEvent()

    object OnErrorPopupDismissed: LoginFormEvent()
}