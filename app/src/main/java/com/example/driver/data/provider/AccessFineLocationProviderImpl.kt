package com.example.driver.data.provider

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.example.driver.domain.provider.AccessFineLocationProvider
import javax.inject.Inject

class AccessFineLocationProviderImpl @Inject constructor(
    private val context: Context
): AccessFineLocationProvider {

    override fun isAccessFineLocationEnabled(): Boolean {
        return ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
}