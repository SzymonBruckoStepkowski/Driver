package com.example.driver.domain.use_case.send_report

import android.app.Application
import com.example.driver.common.handleApiRequest
import com.example.driver.data.remote.model.ReportRequest
import com.example.driver.data.remote.model.toMultipartBody
import com.example.driver.domain.repository.ReportRepository
import javax.inject.Inject

class SendReportUseCase @Inject constructor(
    private val app: Application,
    private val repository: ReportRepository
) {
    operator fun invoke(report: ReportRequest) = handleApiRequest {
        repository.sendReport(report.toMultipartBody(app.applicationContext))
    }
}