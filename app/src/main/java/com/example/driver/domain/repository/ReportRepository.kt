package com.example.driver.domain.repository

import com.example.driver.data.remote.dto.ReportDto
import com.example.driver.data.remote.model.ReportsResponse
import okhttp3.MultipartBody

interface ReportRepository {

    suspend fun getReport(reportId: String): ReportDto?

    suspend fun getReports(): ReportsResponse?

    suspend fun getReportsWithQueries(
        sort: String,
        perPage: Int,
        filter: String
    ): ReportsResponse?

    suspend fun sendReport(report: MultipartBody): ReportDto?

    suspend fun updateReport(reportId: String, report: MultipartBody): ReportDto?
}