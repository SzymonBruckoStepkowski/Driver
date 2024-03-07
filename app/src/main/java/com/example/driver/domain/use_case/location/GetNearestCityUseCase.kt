package com.example.driver.domain.use_case.location

import com.example.driver.domain.provider.GeoDataProvider
import javax.inject.Inject

class GetNearestCityUseCase @Inject constructor(
    private val geoDataProvider: GeoDataProvider
) {

    operator fun invoke(
        latitude: Double,
        longitude: Double,
        onResult: (String) -> Unit
    ) {
        geoDataProvider.getNearestCityForLocation(
            latitude = latitude,
            longitude = longitude,
            onResult = onResult
        )
    }
}