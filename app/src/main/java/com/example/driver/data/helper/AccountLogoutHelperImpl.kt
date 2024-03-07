package com.example.driver.data.helper

import com.example.driver.domain.helper.AccountLogoutHelper
import com.example.driver.domain.provider.PreferencesProvider
import com.example.driver.domain.provider.TokenProvider
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class AccountLogoutHelperImpl @Inject constructor(
    private val tokenProvider: TokenProvider,
    private val preferencesProvider: PreferencesProvider
): AccountLogoutHelper {

    private val _logoutEvent = MutableSharedFlow<Any>()
    override val logoutEvent = _logoutEvent.asSharedFlow()

    override suspend fun userLogout() {
        tokenProvider.clearTokens()
        preferencesProvider.clearPreferences()
        _logoutEvent.emit(Unit)
    }
}