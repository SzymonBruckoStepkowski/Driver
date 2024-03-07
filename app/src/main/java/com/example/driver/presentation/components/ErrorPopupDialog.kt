package com.example.driver.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.driver.R
import com.example.driver.presentation.ui.theme.Black
import com.example.driver.presentation.ui.theme.DarkRed
import com.example.driver.presentation.ui.theme.MediumRed
import com.example.driver.presentation.ui.theme.xLightGray
import com.example.driver.presentation.util.WindowCenterOffsetPositionProvider
import com.example.driver.util.UiText

@Composable
fun ErrorPopupDialog(
    error: UiText?,
    onDismiss: () -> Unit
) {
    if (error != null) {

        Popup(
            popupPositionProvider = WindowCenterOffsetPositionProvider(),
            onDismissRequest = onDismiss,
        ) {
            Surface(
                border = BorderStroke(1.dp, MediumRed),
                shape = RoundedCornerShape(8.dp),
                color = xLightGray,
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.error_txt),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h6,
                        color = DarkRed
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = error.asString(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body2,
                        color = Black
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onDismiss
                    ) {
                        Text(
                            text = stringResource(id = R.string.ok_text),
                            style = MaterialTheme.typography.body2
                        )
                    }
                }
            }
        }
    }
}