package com.example.driver.domain.use_case.update_report

import android.app.Application
import com.example.driver.common.handleApiRequest
import com.example.driver.data.remote.model.ReportRequest
import com.example.driver.data.remote.model.toMultipartBody
import com.example.driver.domain.model.Report
import com.example.driver.domain.repository.ReportRepository
import javax.inject.Inject

class UpdateReportUseCase @Inject constructor(
    private val app: Application,
    private val repository: ReportRepository
) {
    operator fun invoke(editedReport: Report, report: ReportRequest) = handleApiRequest {
        repository.updateReport(
            reportId = editedReport.id,
            report = report.toMultipartBody(
                context = app.applicationContext,
                currentFiles = editedReport.attachments
            ))
    }
}