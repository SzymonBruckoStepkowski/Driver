package com.example.driver.presentation.start.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.driver.R
import com.example.driver.common.Resource
import com.example.driver.domain.use_case.login.LoginUseCase
import com.example.driver.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    private val _effect = MutableSharedFlow<LoginEvent>()
    val effect = _effect.asSharedFlow()

    fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.OnLoginBtnClicked -> {
                login(event.login, event.password)
            }
            is LoginFormEvent.OnErrorPopupDismissed -> {
                _state.value = _state.value.copy(error = null)
            }
        }
    }
    private fun login(login: String, password: String) {
        loginUseCase(login, password).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data == true) {
                        _state.value = LoginState()
                        _effect.emit(LoginEvent.LoggedIn)
                    } else {
                        _state.value = LoginState(
                            error = UiText.StringResource(R.string.wrong_credentials_error)
                        )
                    }
                }
                is Resource.Loading -> {
                    _state.value = LoginState(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = LoginState(
                        error = UiText.DynamicString(result.message.orEmpty())
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}