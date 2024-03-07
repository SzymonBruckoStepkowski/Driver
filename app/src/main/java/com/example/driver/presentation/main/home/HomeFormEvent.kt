package com.example.driver.presentation.main.home

sealed class HomeFormEvent {
    object OnErrorPopupDismissed: HomeFormEvent()
}