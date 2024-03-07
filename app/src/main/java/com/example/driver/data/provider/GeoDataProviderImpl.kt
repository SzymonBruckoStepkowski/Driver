package com.example.driver.data.provider

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import com.example.driver.R
import com.example.driver.domain.provider.GeoDataProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GeoDataProviderImpl @Inject constructor(
    val context: Context
): GeoDataProvider {

    private val maxResult = 10

    private val geocoder: Geocoder = Geocoder(context)

    @SuppressWarnings("deprecation")
    override fun getNearestCityForLocation(
        latitude: Double,
        longitude: Double,
        onResult: (String) -> Unit
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(latitude, longitude, maxResult) {
                onResult.invoke(selectCityName(it))
            }
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    geocoder.getFromLocation(latitude, longitude, maxResult)?.also {
                        onResult.invoke(selectCityName(it))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun selectCityName(addressList: List<Address>): String {
        val address = addressList.firstOrNull()

        if (address != null && !address.locality.isNullOrEmpty()) {
            return address.locality
        }

        if (address != null && !address.adminArea.isNullOrEmpty()) {
            return address.adminArea
        }

        return context.getString(R.string.not_applicable_abbr)
    }
}