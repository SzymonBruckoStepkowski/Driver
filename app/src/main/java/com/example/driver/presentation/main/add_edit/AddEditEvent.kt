package com.example.driver.presentation.main.add_edit

import com.example.driver.domain.model.EventType

sealed class AddEditEvent {
    object ItemSaved : AddEditEvent()

    data class InitializeFields(
        val eventType: EventType? = null,
        val counterValue: Long? = null,
        val attachments: List<String>? = null
    ): AddEditEvent()
}