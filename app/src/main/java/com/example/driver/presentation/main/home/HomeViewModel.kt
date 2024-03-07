package com.example.driver.presentation.main.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.driver.common.Resource
import com.example.driver.domain.use_case.check_user_presence.CheckUserPresenceUseCase
import com.example.driver.domain.use_case.get_user.GetUserExtendedUseCase
import com.example.driver.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserExtendedUseCase: GetUserExtendedUseCase,
    private val checkUserPresenceUseCase: CheckUserPresenceUseCase
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    init {
        getUserData()
    }

    fun onEvent(event: HomeFormEvent) {
        when (event) {
            is HomeFormEvent.OnErrorPopupDismissed -> {
                _state.value = _state.value.copy(error = null)
            }
        }
    }

    private fun getUserData() {
        getUserExtendedUseCase().zip(checkUserPresenceUseCase()) { user, presence ->

            if (presence is Resource.Error) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = UiText.DynamicString(presence.message.orEmpty())
                )
            }

            when (user) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        user = user.data,
                        isUserActive = presence.data ?: false,
                        isLoading = false
                    )
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
                        error = UiText.DynamicString(user.message.orEmpty())
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}