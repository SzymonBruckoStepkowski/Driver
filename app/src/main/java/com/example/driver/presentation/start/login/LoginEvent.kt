package com.example.driver.presentation.start.login

sealed class LoginEvent {
    object LoggedIn : LoginEvent()
}