package com.example.driver.data.provider

import android.content.SharedPreferences
import com.example.driver.common.formatToApiDateTimeString
import com.example.driver.domain.provider.TokenProvider
import java.util.*
import javax.inject.Inject

class TokenProviderImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
): TokenProvider {

    companion object {
        const val SP_TOKEN = "com.akra.driver.data.provider.SP_TOKEN"
        const val SP_TOKEN_EXPIRE_DATE = "com.akra.driver.data.provider.SP_TOKEN_EXPIRE_DATE"
        const val SP_REFRESH_TOKEN = "com.akra.driver.data.provider.SP_REFRESH_TOKEN"
        const val SP_REFRESH_EXPIRE_DATE = "com.akra.driver.data.provider.SP_REFRESH_EXPIRE_DATE"
        const val SP_CREATE_TOKENS_DATE = "com.akra.driver.data.provider.SP_CREATE_TOKENS_DATE"
    }

    override fun saveTokens(
        accessToken: String,
        refreshToken: String?,
        accessTokenExpireDate: String?,
        refreshTokenExpireDate: String?
    ) {
        val createDate = Calendar.getInstance().time.formatToApiDateTimeString()

        with(sharedPreferences) {
            //in this place data have to be save synchronously in order to avoid race condition
            edit().putString(SP_CREATE_TOKENS_DATE, createDate).apply()

            edit().putString(SP_TOKEN, accessToken).apply()
            edit().putString(SP_TOKEN_EXPIRE_DATE, accessTokenExpireDate).apply()

            edit().putString(SP_REFRESH_TOKEN, refreshToken).apply()
            edit().putString(SP_REFRESH_EXPIRE_DATE, refreshTokenExpireDate).apply()
        }
    }

    override fun getToken(): String? {
        return sharedPreferences.getString(SP_TOKEN, null)
    }

    override fun getRefreshToken(): String? {
        return sharedPreferences.getString(SP_REFRESH_TOKEN, null)
    }

    override fun getAccessTokenCreateAndExpireDates(): Pair<String?, String?> {
        val createDate = sharedPreferences.getString(SP_CREATE_TOKENS_DATE, null)
        val expireDate = sharedPreferences.getString(SP_TOKEN_EXPIRE_DATE, null)
        return Pair(createDate, expireDate)
    }

    override fun getRefreshTokenCreateAndExpireDates(): Pair<String?, String?> {
        val createDate = sharedPreferences.getString(SP_CREATE_TOKENS_DATE, null)
        val expireDate = sharedPreferences.getString(SP_TOKEN_EXPIRE_DATE, null)
        return Pair(createDate, expireDate)
    }

    override fun clearTokens() {
        with(sharedPreferences) {
            edit().remove(SP_TOKEN).apply()
            edit().remove(SP_REFRESH_TOKEN).apply()
            edit().remove(SP_CREATE_TOKENS_DATE).apply()
            edit().remove(SP_REFRESH_EXPIRE_DATE).apply()
            edit().remove(SP_TOKEN_EXPIRE_DATE).apply()
        }
    }
}