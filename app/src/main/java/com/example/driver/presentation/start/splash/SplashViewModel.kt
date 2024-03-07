package com.example.driver.presentation.start.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.driver.domain.use_case.login.IsLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isLoggedInUseCase: IsLoggedInUseCase
) : ViewModel() {

    private val _effect = MutableSharedFlow<SplashEvent>()
    val effect = _effect.asSharedFlow()

    init {
        checkIfUserIsLoggedIn()
    }

    private fun checkIfUserIsLoggedIn() {
        isLoggedInUseCase().onEach { result ->
            when (result) {
                true -> {
                    _effect.emit(SplashEvent.LoggedIn)
                }
                false -> {
                    _effect.emit(SplashEvent.LoggedOut)
                }
            }
        }.launchIn(viewModelScope)
    }
}