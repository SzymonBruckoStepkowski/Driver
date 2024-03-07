package com.example.driver.data.remote.model

import com.example.driver.data.remote.dto.UserDto
import com.example.driver.data.remote.dto.VehicleDto

data class ExpandStructure(
    val driver_id: UserDto,
    val vehicle_id: VehicleDto,
    val trailer_id: VehicleDto
)