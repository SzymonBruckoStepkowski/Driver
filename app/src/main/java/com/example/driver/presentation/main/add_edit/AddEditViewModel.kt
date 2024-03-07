package com.example.driver.presentation.main.add_edit

import androidx.compose.runtime.*
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.driver.R
import com.example.driver.common.Constants
import com.example.driver.common.Resource
import com.example.driver.data.remote.model.ReportRequest
import com.example.driver.domain.model.EventType
import com.example.driver.domain.model.Report
import com.example.driver.domain.use_case.get_report.GetReportUseCase
import com.example.driver.domain.use_case.get_user.GetUserExtendedUseCase
import com.example.driver.domain.use_case.location.GetLocationUseCase
import com.example.driver.domain.use_case.location.GetNearestCityUseCase
import com.example.driver.domain.use_case.location.IsAccessFineLocationEnabledUseCase
import com.example.driver.domain.use_case.location.IsGpsEnabledUseCase
import com.example.driver.domain.use_case.send_report.SendReportUseCase
import com.example.driver.domain.use_case.update_report.UpdateReportUseCase
import com.example.driver.domain.use_case.validation.ValidateBlankFieldsUseCase
import com.example.driver.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val getReportUseCase: GetReportUseCase,
    private val sendReportUseCase: SendReportUseCase,
    private val updateReportUseCase: UpdateReportUseCase,
    private val getUserExtendedUseCase: GetUserExtendedUseCase,
    private val validateBlankFieldsUseCase: ValidateBlankFieldsUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    private val isAccessFineLocationEnabledUseCase: IsAccessFineLocationEnabledUseCase,
    private val isGpsEnabledUseCase: IsGpsEnabledUseCase,
    private val getNearestCityUseCase: GetNearestCityUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(AddEditState())
    val state: State<AddEditState> = _state

    private val _effect = MutableSharedFlow<AddEditEvent>()
    val effect = _effect.asSharedFlow().shareIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        replay = 1
    )

    init {
        with(savedStateHandle) {
            if (contains(Constants.PARAM_REPORT_ID)) {
                get<String>(Constants.PARAM_REPORT_ID)?.let {
                    getDataForEditMode(reportId = it)
                }
            } else {
                _state.value = _state.value.copy(
                    popupLocation = isAccessFineLocationEnabledUseCase() && !isGpsEnabledUseCase()
                )
                getDataForAddMode(
                    get<String>(Constants.PARAM_OPERATION_TYPE)?.let {
                        EventType.valueOf(it.uppercase())
                    }
                )
            }
        }
    }

    fun onEvent(event: AddEditFormEvent) {
        when (event) {
            is AddEditFormEvent.PopupControlDismissed -> {
                _state.value = _state.value.copy(popupControl = false)
            }
            is AddEditFormEvent.PopupLocationDismissed -> {
                _state.value = _state.value.copy(popupLocation = false)
            }
            is AddEditFormEvent.OnSaveBtnClicked -> with(event) {
                onSaveClicked(
                    counterValueState = counterValueState
                )
            }
            is AddEditFormEvent.OnSaveAccepted -> with(event) {
                onSaveAccepted(
                    eventTypeState = eventTypeState,
                    counterValueState = counterValueState,
                    attachmentsState = attachmentsState
                )
            }
            is AddEditFormEvent.OnErrorPopupDismissed -> {
                _state.value = _state.value.copy(error = null)
            }
        }
    }

    private fun onSaveClicked(
        counterValueState: String
    ) {
        val validationResult = validateBlankFieldsUseCase(
            listOf(
                counterValueState
            )
        )

        _state.value = _state.value.copy(
            popupControl = validationResult.successful,
            error = validationResult.errorMessage
        )
    }

    private fun onSaveAccepted(
        eventTypeState: EventType,
        counterValueState: String,
        attachmentsState: List<String>
    ) {
        _state.value = _state.value.copy(popupControl = false)

        selectReportRequestCase(
            editMode = _state.value.editMode,
            eventTypeState = eventTypeState,
            counterValueState = counterValueState,
            attachmentsState = attachmentsState,
            location = getCurrentLocation()
        )
    }

    private fun selectReportRequestCase(
        editMode: Boolean,
        eventTypeState: EventType,
        counterValueState: String,
        attachmentsState: List<String>,
        location: Pair<String?, String?>
    ) {
        val latitude = location.first?.toDouble()
        val longitude = location.second?.toDouble()

        if (editMode) {
            pushReportRequest(
                editMode = true,
                eventTypeState = eventTypeState,
                counterValueState = counterValueState,
                attachmentsState = attachmentsState
            )
        } else if (latitude != null && longitude != null) {
            getNearestCityUseCase.invoke(
                latitude = latitude,
                longitude = longitude
            ) { city ->
                pushReportRequest(
                    editMode = false,
                    eventTypeState = eventTypeState,
                    counterValueState = counterValueState,
                    attachmentsState = attachmentsState,
                    latitude = location.first,
                    longitude = location.second,
                    locality = city
                )
            }
        } else {
            pushReportRequest(
                editMode = false,
                eventTypeState = eventTypeState,
                counterValueState = counterValueState,
                attachmentsState = attachmentsState
            )
        }
    }
    private fun pushReportRequest(
        editMode: Boolean,
        eventTypeState: EventType,
        counterValueState: String,
        attachmentsState: List<String>,
        latitude: String? = null,
        longitude: String? = null,
        locality: String? = null
    ) {
        if (!editMode) { // Add new report

            val user = _state.value.user

            sendReport(
                report = ReportRequest(
                    data = Date(),
                    mileage = counterValueState.toLong(),
                    geo_lat = latitude,
                    geo_long = longitude,
                    driver_id = user?.id,
                    vehicle_id = user?.vehicleId.orEmpty(),
                    trailer_id = user?.trailerId.orEmpty(),
                    operation_type = eventTypeState.name.lowercase(),
                    city = locality,
                    files = attachmentsState
                )
            )
        } else { // Edit report
            val editedReport = _state.value.item

            editedReport?.let { report ->
                updateReport(
                    editedReport = report,
                    report = ReportRequest(
                        data = editedReport.createdDate,
                        mileage = counterValueState.toLong(),
                        geo_long = editedReport.lastLocLongitude,
                        geo_lat = editedReport.lastLocLatitude,
                        city = editedReport.city,
                        driver_id = editedReport.driverId,
                        vehicle_id = editedReport.vehicleId,
                        trailer_id = editedReport.trailerId,
                        operation_type = eventTypeState.name.lowercase(),
                        files = attachmentsState
                    )
                )
            }
        }
    }

    private fun getCurrentLocation(): Pair<String?, String?> {
        val isEditMode = _state.value.editMode
        if (isEditMode) {
            return Pair(null, null)
        }

        val isLocationAvailable = isAccessFineLocationEnabledUseCase() && isGpsEnabledUseCase()
        if (!isLocationAvailable) {
            return Pair(null, null)
        }

        return getLocationUseCase()
    }

    private fun getDataForAddMode(eventType: EventType?) {
        getUserExtendedUseCase().onEach { user ->
            handleResourceResult(user) {
                _state.value = _state.value.copy(
                    user = user.data
                )
                user.data?.run {
                    _effect.emit(AddEditEvent.InitializeFields(
                        eventType = eventType,
                        counterValue = vehicle?.mileage
                    ))
                }
            }
        }.launchIn(viewModelScope)
    }
    private fun getDataForEditMode(reportId: String) {
        getReportUseCase(reportId).onEach { report ->
            handleResourceResult(report) {
                _state.value = _state.value.copy(
                    item = report.data
                )
                report.data?.run {
                    _effect.emit(AddEditEvent.InitializeFields(
                        eventType = eventType,
                        counterValue = counterValue,
                        attachments = attachments
                    ))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun sendReport(report: ReportRequest) {
        sendReportUseCase(report).onEach { result ->
            handleResourceResult(result) {
                if (result.data != null) {
                    _effect.emit(AddEditEvent.ItemSaved)
                } else {
                    _state.value = _state.value.copy(error = UiText.StringResource(R.string.saving_error))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateReport(editedReport: Report, report: ReportRequest) {
        updateReportUseCase(editedReport, report).onEach { result ->
            handleResourceResult(result) {
                if (result.data != null) {
                    _effect.emit(AddEditEvent.ItemSaved)
                } else {
                    _state.value = _state.value.copy(error = UiText.StringResource(R.string.saving_error))
                }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun <T> handleResourceResult(result: Resource<T>, onSuccess: suspend () -> Unit) {
        when (result) {
            is Resource.Success -> {
                _state.value = _state.value.copy(isLoading = false)
                onSuccess.invoke()
            }
            is Resource.Loading -> {
                _state.value = _state.value.copy(
                    isLoading = true,
                    error = null
                )
            }
            is Resource.Error -> {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = UiText.DynamicString(result.message.orEmpty())
                )
            }
        }
    }
}