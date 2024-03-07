package com.example.driver.domain.provider

interface GeoDataProvider {

    fun getNearestCityForLocation(
        latitude: Double,
        longitude: Double,
        onResult: (String) -> Unit
    )
}