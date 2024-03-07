package com.example.driver.data.provider

import android.location.LocationManager
import com.example.driver.domain.provider.GpsProvider
import javax.inject.Inject

class GpsProviderImpl @Inject constructor(
    private val locationManager: LocationManager
): GpsProvider {

    override fun isGpsProviderEnabled(): Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}