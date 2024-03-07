package com.example.driver.domain.use_case.location

import com.example.driver.domain.provider.GpsProvider
import javax.inject.Inject

class IsGpsEnabledUseCase @Inject constructor(
    private val gpsProvider: GpsProvider
) {
    operator fun invoke(): Boolean {
        return gpsProvider.isGpsProviderEnabled()
    }
}