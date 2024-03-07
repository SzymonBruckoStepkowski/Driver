package com.example.driver.data.repository

import com.example.driver.common.handleApiResponse
import com.example.driver.data.remote.DriverApi
import com.example.driver.data.remote.model.AuthenticateRequest
import com.example.driver.data.remote.model.AuthenticateResponse
import com.example.driver.domain.repository.LoginRepository
import javax.inject.Inject
import javax.inject.Named

class LoginRepositoryImpl @Inject constructor(
    @Named("driverApiForLogin") private val api: DriverApi
) : LoginRepository {

    override suspend fun login(login: String, password: String): AuthenticateResponse? {
        val result = api.authenticate(
            authBody = AuthenticateRequest(
                identity = login,
                password = password
            ))

        return handleApiResponse(result)
    }
}