package com.example.driver.presentation.main.profile

sealed class ProfileFormEvent {

    object OnSaveBtnClicked: ProfileFormEvent()

    data class OnSaveAccepted(
        val truckIdState: String,
        val trailerIdState: String,
    ): ProfileFormEvent()
    object PopupControlDismissed: ProfileFormEvent()
    object LogoutUser: ProfileFormEvent()

    object OnErrorPopupDismissed: ProfileFormEvent()
}