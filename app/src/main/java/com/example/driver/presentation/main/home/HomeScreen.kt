package com.example.driver.presentation.main.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.driver.R
import com.example.driver.common.Constants
import com.example.driver.domain.model.EventType
import com.example.driver.presentation.MainScreen
import com.example.driver.presentation.components.ErrorPopupDialog
import com.example.driver.presentation.components.ProgressField
import com.example.driver.presentation.main.home.components.StatusField
import com.example.driver.presentation.main.home.components.TileGradientButton
import com.example.driver.presentation.ui.theme.LightGreen
import com.example.driver.presentation.ui.theme.LightRed
import com.example.driver.presentation.ui.theme.MediumGreen
import com.example.driver.presentation.ui.theme.MediumRed

@Composable
fun HomeScreen(
    navController: NavController,
    state: HomeState,
    onEvent: (HomeFormEvent) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier
                    .padding(16.dp),
                style = MaterialTheme.typography.h6,
                text = stringResource(R.string.home_screen_title)
            )

            Divider()

            Row(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = stringResource(R.string.home_vehicle_title),
                        style = MaterialTheme.typography.body1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = stringResource(R.string.home_number_txt, state.user?.vehicle?.numberPlates
                            ?: stringResource(id = R.string.not_applicable_abbr)
                        ),
                        style = MaterialTheme.typography.body1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = stringResource(R.string.home_trailer_title),
                        style = MaterialTheme.typography.body1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = stringResource(R.string.home_number_txt, state.user?.trailer?.numberPlates
                            ?: stringResource(id = R.string.not_applicable_abbr)
                        ),
                        style = MaterialTheme.typography.body1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                }
            }

            Row(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TileGradientButton(
                        modifier = Modifier.fillMaxWidth(),
                        btnText = stringResource(R.string.home_loading_btn_text),
                        onClick = {
                            navController.navigate(MainScreen.AddEditScreen.route +
                                    "?${Constants.PARAM_OPERATION_TYPE}=${EventType.LOADING.name}"
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    TileGradientButton(
                        modifier = Modifier.fillMaxWidth(),
                        btnText = stringResource(R.string.home_resume_btn_text),
                        gradientColors = listOf(LightGreen, MediumGreen),
                        onClick = {
                            navController.navigate(MainScreen.AddEditScreen.route +
                                    "?${Constants.PARAM_OPERATION_TYPE}=${EventType.DRIVING.name}"
                            )
                        }
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TileGradientButton(
                        modifier = Modifier.fillMaxWidth(),
                        btnText = stringResource(R.string.home_unloading_btn_text),
                        onClick = {
                            navController.navigate(MainScreen.AddEditScreen.route +
                                    "?${Constants.PARAM_OPERATION_TYPE}=${EventType.UNLOADING.name}"
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    TileGradientButton(
                        modifier = Modifier.fillMaxWidth(),
                        btnText = stringResource(R.string.home_pause_btn_text),
                        gradientColors = listOf(LightRed, MediumRed),
                        onClick = {
                            navController.navigate(MainScreen.AddEditScreen.route +
                                    "?${Constants.PARAM_OPERATION_TYPE}=${EventType.PAUSE.name}"
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            StatusField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                isActive = state.isUserActive
            )
        }

        ErrorPopupDialog(
            error = state.error,
            onDismiss = {
                onEvent(HomeFormEvent.OnErrorPopupDismissed)
            }
        )

        ProgressField(
            isLoading = state.isLoading,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}