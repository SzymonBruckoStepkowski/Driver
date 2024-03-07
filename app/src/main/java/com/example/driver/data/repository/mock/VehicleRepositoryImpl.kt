package com.example.driver.data.repository.mock

import com.example.driver.data.remote.DriverApi
import com.example.driver.data.remote.dto.VehicleDto
import com.example.driver.data.remote.model.VehiclesResponse
import com.example.driver.domain.model.VehicleType
import com.example.driver.domain.repository.VehicleRepository
import java.util.Date
import javax.inject.Inject

class VehicleRepositoryImpl @Inject constructor(
    private val api: DriverApi
) : VehicleRepository {

    override suspend fun getVehicles(): VehiclesResponse? {
        return VehiclesResponse(
            page = 1,
            perPage = 10,
            totalPages = 1,
            totalItems = 2,
            items = listOf(
                VehicleDto(
                    id = "ABCD",
                    collectionId = "abcd",
                    collectionName = "collectionName",
                    created = Date(),
                    updated = Date(),
                    mileage = 100,
                    number_plates = "ABCD",
                    vehicle_type = VehicleType.TRUCK.name
                ),
                VehicleDto(
                    id = "EFGH",
                    collectionId = "abcd",
                    collectionName = "collectionName",
                    created = Date(),
                    updated = Date(),
                    mileage = 100,
                    number_plates = "EFGH",
                    vehicle_type = VehicleType.TRAILER.name
                )
            )
        )
    }
}