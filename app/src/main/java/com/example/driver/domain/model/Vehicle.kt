package com.example.driver.domain.model

import com.example.driver.R
import com.example.driver.util.UiText

data class Vehicle(
    val id: String?,
    val numberPlates: String?,
    val vehicleType: VehicleType,
    val mileage: Long
)

enum class VehicleType(val label: UiText) {
    UNKNOWN(UiText.StringResource(R.string.unknown_vehicle_label)),
    TRUCK(UiText.StringResource(R.string.truck_vehicle_label)),
    TRAILER(UiText.StringResource(R.string.trailer_vehicle_label));

    companion object {
        fun matchVehicleType(value: Int): VehicleType {
            return when (value) {
                1 -> TRUCK
                2 -> TRAILER
                else -> UNKNOWN
            }
        }
    }
}