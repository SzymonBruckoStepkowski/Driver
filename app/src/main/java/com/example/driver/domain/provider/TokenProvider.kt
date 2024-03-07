package com.example.driver.domain.provider

interface TokenProvider {

    fun saveTokens(
        accessToken: String,
        refreshToken: String? = null,
        accessTokenExpireDate: String? = null,
        refreshTokenExpireDate: String? = null
    )

    fun getToken(): String?

    fun getRefreshToken(): String?

    fun getAccessTokenCreateAndExpireDates(): Pair<String?, String?>

    fun getRefreshTokenCreateAndExpireDates(): Pair<String?, String?>

    fun clearTokens()
}