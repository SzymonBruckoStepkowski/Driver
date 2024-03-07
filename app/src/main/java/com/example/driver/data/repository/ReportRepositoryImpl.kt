package com.example.driver.data.repository

import com.example.driver.common.handleApiResponse
import com.example.driver.data.remote.DriverApi
import com.example.driver.data.remote.dto.ReportDto
import com.example.driver.data.remote.model.ReportsResponse
import com.example.driver.domain.repository.ReportRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val api: DriverApi
) : ReportRepository {

    override suspend fun getReport(reportId: String): ReportDto? {
        val result = api.getReportRecord(reportId = reportId)

        return handleApiResponse(result)
    }

    override suspend fun getReports(): ReportsResponse? {
        val result = api.getReports()

        return handleApiResponse(result)
    }

    override suspend fun getReportsWithQueries(
        sort: String,
        perPage: Int,
        filter: String
    ): ReportsResponse? {
        val result = api.getReportsWithQueries(
            sort = sort,
            perPage = perPage,
            filter = filter
        )

        return handleApiResponse(result)
    }

    override suspend fun sendReport(report: MultipartBody): ReportDto? {
        val result = api.sendReport(report = report)

        return handleApiResponse(result)
    }

    override suspend fun updateReport(reportId: String, report: MultipartBody): ReportDto? {
        val result = api.updateReport(reportId = reportId, report = report)

        return handleApiResponse(result)
    }
}