package com.example.driver.domain.use_case.location

import com.example.driver.domain.provider.AccessFineLocationProvider
import javax.inject.Inject

class IsAccessFineLocationEnabledUseCase @Inject constructor(
    private val accessFineLocationProvider: AccessFineLocationProvider
) {
    operator fun invoke(): Boolean {
        return accessFineLocationProvider.isAccessFineLocationEnabled()
    }
}