package com.example.driver.data.provider

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.example.driver.domain.provider.LocationProvider
import com.google.android.gms.location.*
import javax.inject.Inject

class LocationProviderImpl @Inject constructor(
    private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient
): LocationProvider {

    private var coordinates: Pair<Double?, Double?> = Pair(null, null)

    private val locationRequest: LocationRequest =
        LocationRequest
            .Builder(Priority.PRIORITY_HIGH_ACCURACY, UPDATE_INTERVAL_MILLIS)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(MIN_UPDATE_INTERVAL_MILLIS)
            .setMaxUpdateDelayMillis(MAX_UPDATE_INTERVAL_MILLIS)
            .build()

    private val locationCallback: LocationCallback =
        object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.run {
                    coordinates = Pair(latitude, longitude)
                }
                super.onLocationResult(result)
            }
        }

    override fun startLocationUpdates(): Boolean {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            ).isSuccessful
        }
        return false
    }

    override fun getLastLocation(): Pair<Double?, Double?> {
        return coordinates
    }

    companion object {
        const val UPDATE_INTERVAL_MILLIS: Long = 10000
        const val MIN_UPDATE_INTERVAL_MILLIS: Long = 5000
        const val MAX_UPDATE_INTERVAL_MILLIS: Long = 10000
    }
}