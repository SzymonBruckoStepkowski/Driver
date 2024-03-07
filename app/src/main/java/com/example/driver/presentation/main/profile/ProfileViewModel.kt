package com.example.driver.presentation.main.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.driver.R
import com.example.driver.common.Resource
import com.example.driver.data.remote.model.UserRequest
import com.example.driver.domain.model.User
import com.example.driver.domain.model.VehicleType
import com.example.driver.domain.use_case.get_user.GetUserUseCase
import com.example.driver.domain.use_case.get_vehicles.GetVehiclesUseCase
import com.example.driver.domain.use_case.logout.LogoutUseCase
import com.example.driver.domain.use_case.update_user.UpdateUserUseCase
import com.example.driver.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getVehiclesUseCase: GetVehiclesUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    private val _effect = MutableSharedFlow<ProfileEvent>()
    val effect = _effect.asSharedFlow().shareIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        replay = 1
    )

    init {
        getData()
    }

    fun onEvent(event: ProfileFormEvent) {
        when (event) {
            is ProfileFormEvent.OnSaveBtnClicked -> {
                _state.value = _state.value.copy(popupControl = true)
            }
            is ProfileFormEvent.OnSaveAccepted -> {
                _state.value = _state.value.copy(popupControl = false)
                with(event) {
                    updateUser(
                        UserRequest(
                            vehicle_id = truckIdState,
                            trailer_id = trailerIdState
                        )
                    )
                }
            }
            is ProfileFormEvent.PopupControlDismissed -> {
                _state.value = _state.value.copy(popupControl = false)
            }
            is ProfileFormEvent.OnErrorPopupDismissed -> {
                _state.value = _state.value.copy(error = null)
            }
            is ProfileFormEvent.LogoutUser -> logoutUser()
        }
    }

    private fun getData() {
        getUserUseCase().zip(getVehiclesUseCase()) { user, vehicles ->

            handleResourceResult(vehicles) {
                _state.value = _state.value.copy(
                    truckList = vehicles.data?.get(VehicleType.TRUCK).orEmpty(),
                    trailerList = vehicles.data?.get(VehicleType.TRAILER).orEmpty()
                )
            }

            handleResourceResult(user) {
                setNewUserData(user.data)
            }
        }.launchIn(viewModelScope)
    }

    private fun updateUser(userRequest: UserRequest) {
        updateUserUseCase(userRequest).onEach { result ->
            handleResourceResult(result) {
                if (result.data != null) {
                    setNewUserData(result.data)
                } else {
                    _state.value = _state.value.copy(error = UiText.StringResource(R.string.user_update_error))
                }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun setNewUserData(newUser: User?) {
        _state.value = _state.value.copy(
            user = newUser
        )
        _effect.emit(
            ProfileEvent.InitializeFields(
                truckVehicleId = newUser?.vehicleId,
                trailerVehicleId = newUser?.trailerId
            )
        )
    }

    private fun logoutUser() = viewModelScope.launch { logoutUseCase() }

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