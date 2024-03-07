package com.example.driver.domain.use_case.location

import com.example.driver.domain.provider.LocationProvider
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val locationProvider: LocationProvider
) {
    operator fun invoke(): Pair<String?, String?> {
        val coordinates = locationProvider.getLastLocation()

        return Pair(coordinates.first?.toString(), coordinates.second?.toString())
    }
}