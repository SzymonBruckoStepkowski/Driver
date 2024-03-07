package com.example.driver.presentation.main.report_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.driver.R
import com.example.driver.common.Constants
import com.example.driver.presentation.MainScreen
import com.example.driver.presentation.components.ErrorPopupDialog
import com.example.driver.presentation.components.ProgressField
import com.example.driver.presentation.main.report_list.components.ReportListHeader
import com.example.driver.presentation.main.report_list.components.ReportListItem

@Composable
fun ReportListScreen(
    navController: NavController,
    state: ReportListState,
    onEvent: (ReportListFormEvent) -> Unit
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
                text = stringResource(R.string.reports_screen_title)
            )

            Divider()

            ReportListHeader(modifier = Modifier.fillMaxWidth())

            Divider()

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(state.reportList.size) {
                    ReportListItem(
                        item = state.reportList[it],
                        onItemClick = { report ->
                            navController.navigate(MainScreen.AddEditScreen.route +
                                    "?${Constants.PARAM_REPORT_ID}=${report.id}"
                            )
                        }
                    )
                    Divider()
                }
            }
        }

        ErrorPopupDialog(
            error = state.error,
            onDismiss = {
                onEvent(ReportListFormEvent.OnErrorPopupDismissed)
            }
        )

        ProgressField(
            isLoading = state.isLoading,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}