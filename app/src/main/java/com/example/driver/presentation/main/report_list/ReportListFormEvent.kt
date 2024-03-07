package com.example.driver.presentation.main.report_list

sealed class ReportListFormEvent {
    object OnErrorPopupDismissed: ReportListFormEvent()
}