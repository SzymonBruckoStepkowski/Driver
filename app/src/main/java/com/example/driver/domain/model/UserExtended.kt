package com.example.driver.domain.model

data class UserExtended (
    val id: String?,
    val username: String?,
    val vehicleId: String?,
    val vehicle: Vehicle?,
    val trailerId: String?,
    val trailer: Vehicle?
)
