package com.example.driver.presentation.main.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.driver.R
import com.example.driver.domain.model.EventType

@Composable
fun StatusField(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    isActive: Boolean
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement
    ) {
        Text(text = stringResource(id = R.string.home_status_text))

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = when (isActive) {
                true -> EventType.DRIVING.label
                false -> EventType.PAUSE.label
            }.asString(),
            color = when (isActive) {
                true -> EventType.DRIVING.uiColor
                false -> EventType.PAUSE.uiColor
            }
        )
    }
}