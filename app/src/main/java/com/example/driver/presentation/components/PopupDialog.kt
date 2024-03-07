package com.example.driver.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import com.example.driver.R
import com.example.driver.presentation.ui.theme.Black
import com.example.driver.presentation.ui.theme.xLightGray
import com.example.driver.presentation.util.WindowCenterOffsetPositionProvider

@Composable
fun PopupDialog(
    message: String,
    onAcceptRequest: () -> Unit,
    onDismissRequest: () -> Unit
) {
    Popup(
        popupPositionProvider = WindowCenterOffsetPositionProvider(),
        onDismissRequest = onDismissRequest,
    ) {
        Surface(
            border = BorderStroke(1.dp, MaterialTheme.colors.primary),
            shape = RoundedCornerShape(8.dp),
            color = xLightGray,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = message,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body2,
                    color = Black
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = onAcceptRequest
                    ) {
                        Text(
                            text = stringResource(id = R.string.yes_text),
                            style = MaterialTheme.typography.body2
                        )
                    }

                    Button(
                        onClick = onDismissRequest
                    ) {
                        Text(
                            text = stringResource(id = R.string.no_text),
                            style = MaterialTheme.typography.body2
                        )
                    }
                }
            }
        }
    }
}
