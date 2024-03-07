package com.example.driver.domain.model

import androidx.compose.ui.graphics.Color
import com.example.driver.R
import com.example.driver.presentation.ui.theme.LightGray
import com.example.driver.presentation.ui.theme.MediumGray
import com.example.driver.presentation.ui.theme.MediumGreen
import com.example.driver.presentation.ui.theme.MediumRed
import com.example.driver.util.UiText
import java.util.*

data class Report(
    val id: String,
    val driverId: String,
    val driver: User?,
    val eventType: EventType,
    val counterValue: Long,
    val vehicleId: String,
    val vehicle: Vehicle?,
    val trailerId: String,
    val lastLocLatitude: String?,
    val lastLocLongitude: String?,
    val city: String?,
    val createdDate: Date,
    val attachments: List<String>
)

enum class EventType(val label: UiText, val uiColor: Color) {
    UNKNOWN(UiText.StringResource(R.string.unknown_type_label), LightGray),
    LOADING(UiText.StringResource(R.string.loading_type_label), MediumGray),
    UNLOADING(UiText.StringResource(R.string.unloading_type_label), MediumGray),
    DRIVING(UiText.StringResource(R.string.driving_type_label), MediumGreen),
    PAUSE(UiText.StringResource(R.string.pause_type_label), MediumRed);

    companion object {
        fun matchEventType(value: Int): EventType {
            return when (value) {
                1 -> LOADING
                2 -> UNLOADING
                3 -> DRIVING
                4 -> PAUSE
                else -> UNKNOWN
            }
        }
    }
}
