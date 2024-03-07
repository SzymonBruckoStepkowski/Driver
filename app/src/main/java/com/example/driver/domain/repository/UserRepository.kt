package com.example.driver.domain.repository

import com.example.driver.data.remote.dto.UserDto
import com.example.driver.data.remote.model.UserRequest

interface UserRepository {

    suspend fun getUserData(userId: String): UserDto?

    suspend fun getUserDataExtended(userId: String): UserDto?

    suspend fun updateUserData(userId: String, userRequest: UserRequest): UserDto?
}