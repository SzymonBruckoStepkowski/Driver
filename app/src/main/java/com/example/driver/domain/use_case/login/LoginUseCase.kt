package com.example.driver.domain.use_case.login

import com.example.driver.common.handleApiRequest
import com.example.driver.domain.provider.PreferencesProvider
import com.example.driver.domain.provider.TokenProvider
import com.example.driver.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LoginRepository,
    private val tokenProvider: TokenProvider,
    private val preferencesProvider: PreferencesProvider
) {
    operator fun invoke(login: String, password: String) = handleApiRequest {
        repository.login(login, password).also { res ->
            res?.let {
                tokenProvider.saveTokens(accessToken = it.token)
                preferencesProvider.saveUserId(it.record.id)
            }
        } != null
    }
}