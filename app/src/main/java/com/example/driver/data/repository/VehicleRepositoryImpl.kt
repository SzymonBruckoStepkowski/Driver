package com.example.driver.data.repository

import com.example.driver.common.handleApiResponse
import com.example.driver.data.remote.DriverApi
import com.example.driver.data.remote.model.VehiclesResponse
import com.example.driver.domain.repository.VehicleRepository
import javax.inject.Inject

class VehicleRepositoryImpl @Inject constructor(
    private val api: DriverApi
) : VehicleRepository {

    override suspend fun getVehicles(): VehiclesResponse? {
        val result = api.getVehicles()

        return handleApiResponse(result)
    }
}