package com.example.driver.data.remote.model

import com.example.driver.data.remote.dto.UserDto

data class AuthenticateResponse(
    val token: String,
    val record: UserDto
)
