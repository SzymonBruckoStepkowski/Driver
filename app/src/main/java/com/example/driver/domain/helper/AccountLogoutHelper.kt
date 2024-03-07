package com.example.driver.domain.helper

import kotlinx.coroutines.flow.SharedFlow

interface AccountLogoutHelper {

    val logoutEvent: SharedFlow<Any>

    suspend fun userLogout()
}