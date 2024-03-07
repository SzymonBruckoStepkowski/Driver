package com.example.driver.data.remote

import com.example.driver.data.remote.dto.ReportDto
import com.example.driver.data.remote.dto.UserDto
import com.example.driver.data.remote.model.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface DriverApi {

    @POST("refreshToken")
    fun refreshToken(@Header("Refresh-Token") token: String): Response<TokenResponse>

    @POST("users/auth-with-password")
    suspend fun authenticate(@Body authBody: AuthenticateRequest): Response<AuthenticateResponse>

    @GET("users/records/{id}")
    suspend fun getUserRecord(@Path("id") userId: String): Response<UserDto>

    @GET("users/records/{id}?expand=vehicle_id,trailer_id")
    suspend fun getUserRecordExtended(@Path("id") userId: String): Response<UserDto>

    @PATCH("users/records/{id}")
    suspend fun updateUserRecord(@Path("id") userId: String, @Body userBody: UserRequest): Response<UserDto>

    @GET("reports/records/{id}?expand=driver_id,vehicle_id")
    suspend fun getReportRecord(@Path("id") reportId: String): Response<ReportDto>

    @GET("reports/records?expand=driver_id,vehicle_id")
    suspend fun getReports(): Response<ReportsResponse>

    @GET("reports/records")
    suspend fun getReportsWithQueries(
        @Query("sort") sort: String,
        @Query("perPage") perPage: Int,
        @Query("filter") filter: String
    ): Response<ReportsResponse>

    @POST("reports/records")
    suspend fun sendReport(@Body report: MultipartBody): Response<ReportDto>

    @PATCH("reports/records/{id}")
    suspend fun updateReport(@Path("id") reportId: String, @Body report: MultipartBody): Response<ReportDto>

    @GET("vehicles/records")
    suspend fun getVehicles(): Response<VehiclesResponse>
}