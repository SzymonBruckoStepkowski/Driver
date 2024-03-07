package com.example.driver.data.remote.model

data class AuthenticateRequest(
    val identity: String,
    val password: String
)