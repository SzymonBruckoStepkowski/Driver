package com.example.driver.data.repository.mock

import com.example.driver.data.remote.DriverApi
import com.example.driver.data.remote.dto.UserDto
import com.example.driver.data.remote.model.UserRequest
import com.example.driver.domain.repository.UserRepository
import java.util.Date
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: DriverApi
) : UserRepository {

    override suspend fun getUserData(userId: String): UserDto? {
        return UserDto(
            id = "abcd",
            collectionId = "abcd",
            collectionName = "collectionName",
            username = "UserTest",
            verified = true,
            emailVisibility = true,
            email = "test@test.com",
            created = Date(),
            updated = Date(),
            name = "Test",
            avatar = "",
            vehicle_id = "ABCD",
            trailer_id = "EFGH",
            expand = null
        )
    }

    override suspend fun getUserDataExtended(userId: String): UserDto? {
        return null
    }

    override suspend fun updateUserData(userId: String, userRequest: UserRequest): UserDto? {
        return null
    }
}