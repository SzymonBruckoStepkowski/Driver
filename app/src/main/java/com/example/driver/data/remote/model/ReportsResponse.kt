package com.example.driver.data.remote.model

import com.example.driver.data.remote.dto.ReportDto

data class ReportsResponse(
    val page: Int,
    val perPage: Int,
    val totalPages: Int,
    val totalItems: Int,
    val items: List<ReportDto>
)