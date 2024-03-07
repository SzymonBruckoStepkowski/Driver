package com.example.driver.data.remote.dto

import com.example.driver.data.remote.model.ExpandStructure
import com.example.driver.domain.model.User
import com.example.driver.domain.model.UserExtended
import java.util.Date

data class UserDto(
    val id: String,
    val collectionId: String,
    val collectionName: String,
    val username: String,
    val verified: Boolean,
    val emailVisibility: Boolean,
    val email: String,
    val created: Date,
    val updated: Date,
    val name: String,
    val avatar: String,
    val vehicle_id: String,
    val trailer_id: String,
    val expand: ExpandStructure?
)

fun UserDto.toUser(): User {
    return User(
        id = id,
        username = username,
        vehicleId = vehicle_id,
        trailerId = trailer_id
    )
}

fun UserDto.toUserExtended(): UserExtended {
    return UserExtended(
        id = id,
        username = username,
        vehicleId = vehicle_id,
        vehicle = expand?.vehicle_id?.toVehicle(),
        trailerId = trailer_id,
        trailer = expand?.trailer_id?.toVehicle()
    )
}