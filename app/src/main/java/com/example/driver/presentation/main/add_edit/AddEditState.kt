package com.example.driver.presentation.main.add_edit

import com.example.driver.domain.model.Report
import com.example.driver.domain.model.UserExtended
import com.example.driver.util.UiText

data class AddEditState(
    val user: UserExtended? = null,
    val item: Report? = null,
    val numberPlateList: List<Pair<String, String>> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null,

    val popupControl: Boolean = false,
    val popupLocation: Boolean = false
) {
    val editMode: Boolean get() = item != null
    val editModeEnabled: Boolean get() = false
}