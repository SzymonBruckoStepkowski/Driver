package com.example.driver.domain.use_case.get_report

import com.example.driver.common.handleApiRequest
import com.example.driver.data.remote.dto.toReport
import com.example.driver.domain.repository.ReportRepository
import javax.inject.Inject

class GetReportUseCase @Inject constructor(
    private val repository: ReportRepository
) {
    operator fun invoke(reportId: String) = handleApiRequest {
        repository.getReport(reportId)?.toReport()
    }
}