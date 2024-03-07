package com.example.driver.domain.provider

interface LocationProvider {

    fun startLocationUpdates(): Boolean

    fun getLastLocation(): Pair<Double?, Double?>
}