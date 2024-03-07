package com.example.driver.data.repository.mock

import com.example.driver.data.remote.DriverApi
import com.example.driver.data.remote.dto.ReportDto
import com.example.driver.data.remote.model.ReportsResponse
import com.example.driver.domain.model.EventType
import com.example.driver.domain.repository.ReportRepository
import okhttp3.MultipartBody
import java.util.Date
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val api: DriverApi
) : ReportRepository {

    private val reports = List(10) {
        ReportDto(
            id = it.toString(),
            collectionId = "abcd",
            collectionName = "collectionName",
            created = Date(),
            updated = Date(),
            data = Date(),
            mileage = (100 * it).toLong(),
            geo_long = "",
            geo_lat = "",
            city = "City",
            country = "Country",
            address = "Address",
            postal_code = "Postal Code",
            driver_id = "abcd",
            vehicle_id = "ABCD",
            trailer_id = "EFGH",
            operation_type = EventType.matchEventType((it % 4) + 1).name,
            files = emptyList(),
            expand = null
        )
    }

    override suspend fun getReport(reportId: String): ReportDto? {
        return reports.find { it.id == reportId }
    }

    override suspend fun getReports(): ReportsResponse? {
        return ReportsResponse(
            page = 1,
            perPage = 1000,
            totalPages = 1,
            totalItems = 10,
            items = reports.toList()
        )
    }

    override suspend fun getReportsWithQueries(
        sort: String,
        perPage: Int,
        filter: String
    ): ReportsResponse? {
        return getReports()
    }

    override suspend fun sendReport(report: MultipartBody): ReportDto? {
        return null
    }

    override suspend fun updateReport(reportId: String, report: MultipartBody): ReportDto? {
        return null
    }
}