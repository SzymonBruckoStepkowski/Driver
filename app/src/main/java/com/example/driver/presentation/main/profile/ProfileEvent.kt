package com.example.driver.presentation.main.profile

sealed class ProfileEvent {
    data class InitializeFields(
        val truckVehicleId: String? = null,
        val trailerVehicleId: String? = null
    ): ProfileEvent()
}