package com.example.driver.data.remote.model

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiresOn: String?,
    val refreshTokenExpiresOn: String?
)