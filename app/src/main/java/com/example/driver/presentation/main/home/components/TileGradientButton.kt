package com.example.driver.presentation.main.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.driver.presentation.ui.theme.LightGray
import com.example.driver.presentation.ui.theme.MediumGray
import com.example.driver.presentation.ui.theme.TileTextColor
import com.example.driver.presentation.ui.theme.Transparent

@Composable
fun TileGradientButton(
    modifier: Modifier = Modifier,
    btnText: String,
    gradientColors: List<Color> = listOf(LightGray, MediumGray),
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.height(120.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Transparent
        ),
        contentPadding = PaddingValues(),
        onClick = onClick
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.horizontalGradient(colors = gradientColors)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = btnText,
                fontSize = 18.sp,
                color = TileTextColor
            )
        }
    }
}