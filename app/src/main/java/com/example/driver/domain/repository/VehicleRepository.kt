package com.example.driver.domain.repository

import com.example.driver.data.remote.model.VehiclesResponse

interface VehicleRepository {

    suspend fun getVehicles(): VehiclesResponse?
}