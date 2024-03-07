package com.example.driver.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.location.LocationManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.driver.common.Constants.ENCRYPTED_PREFERENCES_FILE_NAME
import com.example.driver.common.Constants.PREFERENCE_NAME
import com.example.driver.data.helper.AccountLogoutHelperImpl
import com.example.driver.data.provider.*
import com.example.driver.data.remote.DriverApi
import com.example.driver.data.repository.LoginRepositoryImpl
import com.example.driver.data.repository.ReportRepositoryImpl
import com.example.driver.data.repository.UserRepositoryImpl
import com.example.driver.data.repository.VehicleRepositoryImpl
import com.example.driver.domain.helper.AccountLogoutHelper
import com.example.driver.domain.provider.*
import com.example.driver.domain.repository.LoginRepository
import com.example.driver.domain.repository.ReportRepository
import com.example.driver.domain.repository.UserRepository
import com.example.driver.domain.repository.VehicleRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Named("sharedPreferences")
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Named("encryptedSharedPreferences")
    @Singleton
    fun provideEncryptedSharedPreferences(app: Application): SharedPreferences {
        val masterKey = MasterKey.Builder(app)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            app,
            ENCRYPTED_PREFERENCES_FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Provides
    @Singleton
    fun providePreferencesProvider(@Named("sharedPreferences") sharedPreferences: SharedPreferences): PreferencesProvider {
        return PreferencesProviderImpl(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideTokenProvider(@Named("encryptedSharedPreferences") sharedPreferences: SharedPreferences): TokenProvider {
        return TokenProviderImpl(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideAccountLogoutHelper(
        tokenProvider: TokenProvider,
        preferencesProvider: PreferencesProvider
    ): AccountLogoutHelper {
        return AccountLogoutHelperImpl(tokenProvider, preferencesProvider)
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }

    @Provides
    @Singleton
    fun provideLocationManager(app: Application): LocationManager {
        return app.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    @Provides
    @Singleton
    fun provideGpsProvider(locationManager: LocationManager): GpsProvider {
        return GpsProviderImpl(locationManager)
    }

    @Provides
    @Singleton
    fun provideAccessFineLocationProvider(app: Application): AccessFineLocationProvider {
        return AccessFineLocationProviderImpl(app)
    }

    @Provides
    @Singleton
    fun provideLocationProvider(
        app: Application,
        fusedLocationClient: FusedLocationProviderClient
    ): LocationProvider {
        return LocationProviderImpl(app, fusedLocationClient)
    }

    @Provides
    @Singleton
    fun provideGeoDataProvider(app: Application): GeoDataProvider {
        return GeoDataProviderImpl(app)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(@Named("driverApiForLogin") api: DriverApi): LoginRepository {
        return LoginRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideUserRepository(api: DriverApi): UserRepository {
        return UserRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideReportRepository(api: DriverApi): ReportRepository {
        return ReportRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideVehicleRepository(api: DriverApi): VehicleRepository {
        return VehicleRepositoryImpl(api)
    }
}