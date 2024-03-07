package com.example.driver.presentation.main.report_list

import com.example.driver.domain.model.Report
import com.example.driver.util.UiText

data class ReportListState(
    val reportList: List<Report> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null
)