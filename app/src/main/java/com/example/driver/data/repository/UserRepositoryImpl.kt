package com.example.driver.data.repository

import com.example.driver.common.handleApiResponse
import com.example.driver.data.remote.DriverApi
import com.example.driver.data.remote.dto.UserDto
import com.example.driver.data.remote.model.UserRequest
import com.example.driver.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: DriverApi
) : UserRepository {

    override suspend fun getUserData(userId: String): UserDto? {
        val result = api.getUserRecord(userId = userId)

        return handleApiResponse(result)
    }

    override suspend fun getUserDataExtended(userId: String): UserDto? {
        val result = api.getUserRecordExtended(userId = userId)

        return handleApiResponse(result)
    }

    override suspend fun updateUserData(userId: String, userRequest: UserRequest): UserDto? {
        val result = api.updateUserRecord(userId = userId, userBody = userRequest)

        return handleApiResponse(result)
    }
}