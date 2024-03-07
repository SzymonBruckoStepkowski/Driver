package com.example.driver.presentation.main.report_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.driver.common.Resource
import com.example.driver.domain.use_case.get_reports.GetReportsUseCase
import com.example.driver.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ReportListViewModel @Inject constructor(
    private val getReportsUseCase: GetReportsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ReportListState())
    val state: State<ReportListState> = _state

    init {
        getReports()
    }

    fun onEvent(event: ReportListFormEvent) {
        when (event) {
            is ReportListFormEvent.OnErrorPopupDismissed -> {
                _state.value = _state.value.copy(error = null)
            }
        }
    }

    private fun getReports() {
        getReportsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ReportListState(reportList = result.data?.reversed().orEmpty())
                }
                is Resource.Loading -> {
                    _state.value = ReportListState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = ReportListState(error = UiText.DynamicString(result.message.orEmpty()))
                }
            }
        }.launchIn(viewModelScope)
    }
}