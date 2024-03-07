package com.example.driver.domain.use_case.get_vehicles

import com.example.driver.common.handleApiRequest
import com.example.driver.data.remote.dto.toVehicle
import com.example.driver.domain.repository.VehicleRepository
import javax.inject.Inject

class GetVehiclesUseCase @Inject constructor(
    private val repository: VehicleRepository
) {
    operator fun invoke() = handleApiRequest {
        repository.getVehicles()?.items?.map { it.toVehicle() }?.groupBy { it.vehicleType }
    }
}