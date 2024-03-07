package com.example.driver.domain.repository

import com.example.driver.data.remote.model.AuthenticateResponse

interface LoginRepository {

    suspend fun login(login: String, password: String): AuthenticateResponse?
}