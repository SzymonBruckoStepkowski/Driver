package com.example.driver.presentation.start.splash

sealed class SplashEvent {
    object LoggedIn: SplashEvent()
    object LoggedOut: SplashEvent()
}