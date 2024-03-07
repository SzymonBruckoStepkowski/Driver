package com.example.driver.data.remote.dto

import com.example.driver.data.remote.model.ExpandStructure
import com.example.driver.domain.model.EventType
import com.example.driver.domain.model.Report
import java.util.*

data class ReportDto(
    val id: String,
    val collectionId: String,
    val collectionName: String,
    val created: Date,
    val updated: Date,
    val data: Date,
    val mileage: Long,
    val geo_long: String,
    val geo_lat: String,
    val city: String,
    val country: String,
    val address: String,
    val postal_code: String,
    val driver_id: String,
    val vehicle_id: String,
    val trailer_id: String,
    val operation_type: String,
    val files: List<String>,
    val expand: ExpandStructure?
)

fun ReportDto.toReport(): Report {
    return Report(
        id = id,
        driverId = driver_id,
        driver = expand?.driver_id?.toUser(),
        eventType = EventType.valueOf(operation_type.uppercase()),
        counterValue = mileage,
        vehicleId = vehicle_id,
        vehicle = expand?.vehicle_id?.toVehicle(),
        trailerId = trailer_id,
        lastLocLatitude = geo_lat,
        lastLocLongitude = geo_long,
        city = city,
        createdDate = created,
        attachments = files
    )
}