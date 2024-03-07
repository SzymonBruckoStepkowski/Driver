package com.example.driver.presentation.main.add_edit

import com.example.driver.domain.model.EventType

sealed class AddEditFormEvent {
    data class OnSaveBtnClicked(
        val counterValueState: String
    ): AddEditFormEvent()

    data class OnSaveAccepted(
        val eventTypeState: EventType,
        val counterValueState: String,
        val attachmentsState: List<String>
    ): AddEditFormEvent()
    object PopupControlDismissed: AddEditFormEvent()

    object PopupLocationDismissed: AddEditFormEvent()

    object OnErrorPopupDismissed: AddEditFormEvent()
}