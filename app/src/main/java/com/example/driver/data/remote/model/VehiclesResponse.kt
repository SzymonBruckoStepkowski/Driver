package com.example.driver.data.remote.model

import com.example.driver.data.remote.dto.VehicleDto

data class VehiclesResponse(
    val page: Int,
    val perPage: Int,
    val totalPages: Int,
    val totalItems: Int,
    val items: List<VehicleDto>
)