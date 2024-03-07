package com.example.driver.domain.use_case.login

import com.example.driver.domain.provider.TokenProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IsLoggedInUseCase @Inject constructor(
    private val tokenProvider: TokenProvider
) {
    operator fun invoke() = flow {
        delay(2000L)
        emit(tokenProvider.getToken() != null)
    }
}