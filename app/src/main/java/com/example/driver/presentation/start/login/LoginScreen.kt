package com.example.driver.presentation.start.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.driver.R
import com.example.driver.presentation.components.ErrorPopupDialog
import com.example.driver.presentation.components.ProgressField
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    state: LoginState,
    onEvent: (LoginFormEvent) -> Unit,
    effect: SharedFlow<LoginEvent>,
    onLoggedIn: () -> Unit
) {
    var loginTextFieldState by remember {
        mutableStateOf("")
    }
    var passwordTextFieldState by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = true) {
        effect.collectLatest { event ->
            when (event) {
                LoginEvent.LoggedIn -> {
                    onLoggedIn.invoke()
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = loginTextFieldState,
                label = {
                    Text(stringResource(R.string.login_btn_txt))
                },
                onValueChange = {
                    loginTextFieldState = it
                },
                singleLine = true
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = passwordTextFieldState,
                label = {
                    Text(stringResource(R.string.password_txt))
                },
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = {
                    passwordTextFieldState = it
                },
                singleLine = true
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(16.dp),
                onClick = {
                    onEvent(LoginFormEvent.OnLoginBtnClicked(loginTextFieldState, passwordTextFieldState))
                }) {
                Text(text = stringResource(R.string.login_text))
            }
        }

        ErrorPopupDialog(
            error = state.error,
            onDismiss = {
                onEvent(LoginFormEvent.OnErrorPopupDismissed)
            }
        )

        ProgressField(
            isLoading = state.isLoading,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}