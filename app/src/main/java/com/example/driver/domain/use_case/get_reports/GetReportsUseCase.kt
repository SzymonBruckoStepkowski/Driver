package com.example.driver.domain.use_case.get_reports

import com.example.driver.common.handleApiRequest
import com.example.driver.data.remote.dto.toReport
import com.example.driver.domain.repository.ReportRepository
import javax.inject.Inject

class GetReportsUseCase @Inject constructor(
    private val repository: ReportRepository
) {
    operator fun invoke() = handleApiRequest {
        repository.getReports()?.items?.map { it.toReport() }
    }
}