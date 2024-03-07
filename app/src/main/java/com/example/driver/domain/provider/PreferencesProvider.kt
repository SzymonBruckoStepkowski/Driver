package com.example.driver.domain.provider

interface PreferencesProvider {

    fun getUserId(): String?

    fun saveUserId(userId: String)

    fun clearPreferences()
}