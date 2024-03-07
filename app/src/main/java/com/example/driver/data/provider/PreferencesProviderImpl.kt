package com.example.driver.data.provider

import android.content.SharedPreferences
import com.example.driver.domain.provider.PreferencesProvider
import javax.inject.Inject

class PreferencesProviderImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
): PreferencesProvider {

    companion object {
        const val SP_USER_ID = "com.akra.driver.data.provider.SP_USER_ID"
    }

    override fun getUserId(): String? {
        return sharedPreferences.getString(SP_USER_ID, null)
    }

    override fun saveUserId(userId: String) {
        sharedPreferences.edit().putString(SP_USER_ID, userId).apply()
    }

    override fun clearPreferences() {
        with(sharedPreferences) {
            edit().remove(SP_USER_ID).apply()
        }
    }
}