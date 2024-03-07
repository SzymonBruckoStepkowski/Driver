package com.example.driver.domain.use_case.get_user

import com.example.driver.common.handleApiRequest
import com.example.driver.data.remote.dto.toUserExtended
import com.example.driver.domain.provider.PreferencesProvider
import com.example.driver.domain.repository.UserRepository
import javax.inject.Inject

class GetUserExtendedUseCase @Inject constructor(
    private val repository: UserRepository,
    private val preferencesProvider: PreferencesProvider
) {
    operator fun invoke() = handleApiRequest {
        preferencesProvider.getUserId()?.let {
            repository.getUserDataExtended(it)?.toUserExtended()
        }
    }
}