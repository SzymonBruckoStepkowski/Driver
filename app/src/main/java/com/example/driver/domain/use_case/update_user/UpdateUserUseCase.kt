package com.example.driver.domain.use_case.update_user

import com.example.driver.common.handleApiRequest
import com.example.driver.data.remote.dto.toUser
import com.example.driver.data.remote.model.UserRequest
import com.example.driver.domain.provider.PreferencesProvider
import com.example.driver.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val repository: UserRepository,
    private val preferencesProvider: PreferencesProvider
) {
    operator fun invoke(userRequest: UserRequest) = handleApiRequest {
        preferencesProvider.getUserId()?.let {
            repository.updateUserData(it, userRequest)?.toUser()
        }
    }
}