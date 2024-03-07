package com.example.driver.data.repository.mock

import com.example.driver.data.remote.DriverApi
import com.example.driver.data.remote.dto.UserDto
import com.example.driver.data.remote.model.AuthenticateResponse
import com.example.driver.domain.repository.LoginRepository
import java.util.Date
import javax.inject.Inject
import javax.inject.Named

class LoginRepositoryImpl @Inject constructor(
    @Named("driverApiForLogin") private val api: DriverApi
) : LoginRepository {

    override suspend fun login(login: String, password: String): AuthenticateResponse? {
        return AuthenticateResponse(
            token = "TOKEN",
            record = UserDto(
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
        )
    }
}