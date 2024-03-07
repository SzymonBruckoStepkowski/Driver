package com.example.driver.data.remote.dto

import com.example.driver.domain.model.Vehicle
import com.example.driver.domain.model.VehicleType
import java.util.*

data class VehicleDto(
    val id: String,
    val collectionId: String,
    val collectionName: String,
    val created: Date,
    val updated: Date,
    val mileage: Long,
    val number_plates: String,
    val vehicle_type: String
)

fun VehicleDto.toVehicle(): Vehicle {
    return Vehicle(
        id = id,
        numberPlates = number_plates,
        vehicleType = VehicleType.valueOf(vehicle_type.uppercase()),
        mileage = mileage
    )
}
