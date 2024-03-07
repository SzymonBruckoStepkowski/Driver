package com.example.driver.presentation.main.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.driver.R
import com.example.driver.presentation.components.ErrorPopupDialog
import com.example.driver.presentation.components.ExtendedDropdownMenu
import com.example.driver.presentation.components.ProgressField
import com.example.driver.presentation.components.PopupDialog
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileScreen(
    state: ProfileState,
    onEvent: (ProfileFormEvent) -> Unit,
    effect: SharedFlow<ProfileEvent>
) {

    var truckIdFieldState by remember {
        mutableStateOf("")
    }

    var trailerIdFieldState by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = true) {
        effect.collectLatest { event ->
            when(event) {
                is ProfileEvent.InitializeFields -> {
                    event.run {
                        truckVehicleId?.let { truckIdFieldState = it }
                        trailerVehicleId?.let { trailerIdFieldState = it }
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.h6,
                text = stringResource(id = R.string.profile_screen_title),
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                style = MaterialTheme.typography.body2,
                text = stringResource(id = R.string.profile_username_text, state.user?.username.orEmpty()),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                ExtendedDropdownMenu(
                    modifier = Modifier.weight(9f),
                    list = state.truckList.map { it.numberPlates.orEmpty() },
                    value = state.truckList.find { it.id == truckIdFieldState }?.numberPlates.orEmpty(),
                    onValueChange = { index ->
                        truckIdFieldState = state.truckList[index].id.orEmpty()
                    },
                    label = stringResource(R.string.truck_vehicle_label)
                )

                Icon(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            truckIdFieldState = ""
                        },
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "",
                    tint = MaterialTheme.colors.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                ExtendedDropdownMenu(
                    modifier = Modifier.weight(9f),
                    list = state.trailerList.map { it.numberPlates.orEmpty() },
                    value = state.trailerList.find { it.id == trailerIdFieldState }?.numberPlates.orEmpty(),
                    onValueChange = { index ->
                        trailerIdFieldState = state.trailerList[index].id.orEmpty()
                    },
                    label = stringResource(R.string.trailer_vehicle_label)
                )

                Icon(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            trailerIdFieldState = ""
                        },
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "",
                    tint = MaterialTheme.colors.primary
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { onEvent(ProfileFormEvent.OnSaveBtnClicked) },
                enabled = state.user?.run {
                    vehicleId != truckIdFieldState || trailerId != trailerIdFieldState
                } ?: false
            ) {
                Text(text = stringResource(R.string.save_btn_text))
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { onEvent(ProfileFormEvent.LogoutUser) }
            ) {
                Text(text = stringResource(R.string.logout_text))
            }
        }

        if (state.popupControl) {
            PopupDialog(
                message = stringResource(id = R.string.user_update_popup_msg),
                onAcceptRequest = {
                    onEvent(
                        ProfileFormEvent.OnSaveAccepted(
                            truckIdState = truckIdFieldState,
                            trailerIdState = trailerIdFieldState
                        )
                    )
                },
                onDismissRequest = {
                    onEvent(ProfileFormEvent.PopupControlDismissed)
                }
            )
        }

        ErrorPopupDialog(
            error = state.error,
            onDismiss = {
                onEvent(ProfileFormEvent.OnErrorPopupDismissed)
            }
        )

        ProgressField(
            isLoading = state.isLoading,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}