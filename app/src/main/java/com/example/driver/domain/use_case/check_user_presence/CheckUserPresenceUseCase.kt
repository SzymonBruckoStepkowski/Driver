package com.example.driver.domain.use_case.check_user_presence

import com.example.driver.common.handleApiRequest
import com.example.driver.domain.model.EventType
import com.example.driver.domain.repository.ReportRepository
import javax.inject.Inject

class CheckUserPresenceUseCase @Inject constructor(
    private val repository: ReportRepository
) {
    operator fun invoke() = handleApiRequest {
        val response = repository.getReportsWithQueries(
            sort = "-created",
            perPage = 1,
            filter = "operation_type = '${EventType.DRIVING.name.lowercase()}'" +
                    "|| operation_type = '${EventType.PAUSE.name.lowercase()}'"
        )

        val lastRecordType = response?.items?.firstOrNull()?.operation_type

        when (lastRecordType) {
            EventType.DRIVING.name.lowercase() -> true
            else -> false
        }
    }
}