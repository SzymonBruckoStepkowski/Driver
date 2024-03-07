package com.example.driver.presentation.main.add_edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.driver.R
import com.example.driver.common.formatToDateTimeString
import com.example.driver.domain.model.EventType
import com.example.driver.presentation.MainScreen
import com.example.driver.presentation.components.ErrorPopupDialog
import com.example.driver.presentation.components.ProgressField
import com.example.driver.presentation.main.add_edit.components.AttachmentsField
import com.example.driver.presentation.components.ExtendedDropdownMenu
import com.example.driver.presentation.components.PopupDialog
import com.example.driver.presentation.ui.theme.DarkGray
import com.example.driver.presentation.util.ComposeFileProvider
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditScreen(
    navController: NavController,
    state: AddEditState,
    onEvent: (AddEditFormEvent) -> Unit,
    effect: SharedFlow<AddEditEvent>,
    onLocationChecked: () -> Unit
) {
    val context = LocalContext.current

    val focusManager = LocalFocusManager.current

    var eventTypeFieldState by remember {
        mutableStateOf(EventType.UNKNOWN)
    }

    var counterValueFieldState by remember {
        mutableStateOf("")
    }

    var attachmentsUriListState by remember {
        mutableStateOf(emptyList<String>())
    }

    DisposableEffect(key1 = true) {
        onDispose { ComposeFileProvider.clearCache(context = context) }
    }

    LaunchedEffect(key1 = true) {
        effect.collectLatest { event ->
            when(event) {
                AddEditEvent.ItemSaved -> {
                    navController.navigate(MainScreen.HomeScreen.route) {
                        popUpTo(0)
                    }
                }
                is AddEditEvent.InitializeFields -> {
                    event.run {
                        eventType?.let { eventTypeFieldState = it }
                        counterValue?.let { counterValueFieldState = it.toString() }
                        attachments?.let { attachmentsUriListState = it }
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                text = stringResource(R.string.report_item_title),
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(DarkGray)
            )

            if (state.item != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column(
                        modifier = Modifier
                            .weight(3f)
                            .padding(end = 8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.created_date_text, state.item.createdDate.formatToDateTimeString()),
                            style = MaterialTheme.typography.body1,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = stringResource(R.string.report_id_text, state.item.id),
                            style = MaterialTheme.typography.body1,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = stringResource(R.string.driver_id_text, state.item.driver?.username.orEmpty()),
                            style = MaterialTheme.typography.body1,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = stringResource(R.string.vehicle_text, state.item.vehicle?.numberPlates.orEmpty()),
                            style = MaterialTheme.typography.body1,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Column(
                        modifier = Modifier.weight(2f)
                    ) {
                        Text(
                            text = stringResource(R.string.last_location_text),
                            style = MaterialTheme.typography.body2
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        if (!state.item.lastLocLatitude.isNullOrEmpty() && !state.item.lastLocLongitude.isNullOrEmpty()) {
                            Text(
                                text = stringResource(R.string.loc_latitude_text, state.item.lastLocLatitude),
                                style = MaterialTheme.typography.body2,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = stringResource(R.string.loc_longitude_text, state.item.lastLocLongitude),
                                style = MaterialTheme.typography.body2,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = stringResource(R.string.loc_city_text, state.item.city.orEmpty()),
                                style = MaterialTheme.typography.body2,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        } else {
                            Text(
                                text = stringResource(R.string.no_location_text),
                                style = MaterialTheme.typography.body2
                            )
                        }
                    }
                }
            }

            if (state.editMode && state.editModeEnabled) {
                ExtendedDropdownMenu(
                    list = EventType.values().map { it.label.asString() },
                    value = eventTypeFieldState.label.asString(),
                    onValueChange = { index ->
                        eventTypeFieldState = EventType.matchEventType(index)
                    },
                    label = stringResource(R.string.event_type_label)
                )
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.event_type_text),
                        style = MaterialTheme.typography.h6
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = eventTypeFieldState.label.asString(),
                        color = eventTypeFieldState.uiColor,
                        style = MaterialTheme.typography.h6
                    )
                }
            }

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = counterValueFieldState,
                label = {
                    Text(stringResource(R.string.counter_value_label))
                },
                onValueChange = {
                    counterValueFieldState = it
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.NumberPassword
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                singleLine = true,
                enabled = !state.editMode || state.editModeEnabled
            )

            AttachmentsField(
                enabled = !state.editMode || state.editModeEnabled,
                attachmentsList = attachmentsUriListState,
                onAttachmentsListChanged = {
                    attachmentsUriListState = it
                }
            )

            if (!state.editMode || state.editModeEnabled) {
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        onEvent(
                            AddEditFormEvent.OnSaveBtnClicked(
                                counterValueState = counterValueFieldState
                            )
                        )
                    }
                ) {
                    Text(text = stringResource(R.string.save_btn_text))
                }
            }
        }

        if (state.popupLocation) {
            PopupDialog(
                message = stringResource(id = R.string.popup_enable_location),
                onAcceptRequest = {
                    onLocationChecked.invoke()
                    onEvent(AddEditFormEvent.PopupLocationDismissed)
                },
                onDismissRequest = {
                    onEvent(AddEditFormEvent.PopupLocationDismissed)
                }
            )
        }

        if (state.popupControl) {
            PopupDialog(
                message = if (state.item != null) {
                    stringResource(id = R.string.popup_edit_report)
                } else {
                    stringResource(id = R.string.popup_add_report)
                },
                onAcceptRequest = {
                    onEvent(AddEditFormEvent.OnSaveAccepted(
                        eventTypeState = eventTypeFieldState,
                        counterValueState = counterValueFieldState,
                        attachmentsState = attachmentsUriListState
                    ))
                },
                onDismissRequest = {
                    onEvent(AddEditFormEvent.PopupControlDismissed)
                }
            )
        }

        ErrorPopupDialog(
            error = state.error,
            onDismiss = {
                onEvent(AddEditFormEvent.OnErrorPopupDismissed)
            }
        )

        ProgressField(
            isLoading = state.isLoading,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
