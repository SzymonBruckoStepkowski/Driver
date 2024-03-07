package com.example.driver.di

import com.example.driver.BuildConfig
import com.example.driver.data.remote.DriverApi
import com.example.driver.data.remote.api.ApiAuthenticator
import com.example.driver.data.remote.api.AuthorizationInterceptor
import com.example.driver.data.remote.api.DateDeserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.*

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val TIME_OUT = 1L

    @Provides
    @Singleton
    fun provideDateDeserializer(): JsonDeserializer<Date> {
        return DateDeserializer()
    }

    @Provides
    @Singleton
    fun provideGson(dateDeserializer: JsonDeserializer<Date>): Gson {
        return GsonBuilder()
            .registerTypeAdapter(Date::class.java, dateDeserializer)
            .create()
    }

    @Provides
    @Named("okHttpClientForLogin")
    @Singleton
    fun provideOkHttpClientForLogin(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1
        builder.dispatcher(dispatcher)
        builder.connectTimeout(TIME_OUT, TimeUnit.MINUTES)
        builder.readTimeout(TIME_OUT, TimeUnit.MINUTES)
        builder.writeTimeout(TIME_OUT, TimeUnit.MINUTES)

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authorizationInterceptor: AuthorizationInterceptor,
        apiAuthenticator: ApiAuthenticator
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1
        builder.dispatcher(dispatcher)
        builder.connectTimeout(TIME_OUT, TimeUnit.MINUTES)
        builder.readTimeout(TIME_OUT, TimeUnit.MINUTES)
        builder.writeTimeout(TIME_OUT, TimeUnit.MINUTES)
        builder.addInterceptor(authorizationInterceptor)
        builder.authenticator(apiAuthenticator)

        return builder.build()
    }

    @Provides
    @Named("retrofitForLogin")
    @Singleton
    fun provideRetrofitForLogin(gson: Gson, @Named("okHttpClientForLogin") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.base_url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.base_url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Named("driverApiForLogin")
    @Singleton
    fun provideDriverApiForLogin(@Named("retrofitForLogin") retrofit: Retrofit): DriverApi {
        return retrofit.create(DriverApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDriverApi(retrofit: Retrofit): DriverApi {
        return retrofit.create(DriverApi::class.java)
    }
}