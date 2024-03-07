package com.example.driver.presentation.components

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProgressField(
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        CircularProgressIndicator(modifier = modifier)
    }
}