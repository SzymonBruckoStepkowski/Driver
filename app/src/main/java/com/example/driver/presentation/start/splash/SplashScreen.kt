package com.example.driver.presentation.start.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.driver.R
import com.example.driver.presentation.StartScreen
import com.example.driver.presentation.ui.theme.Black
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    navController: NavController,
    effect: SharedFlow<SplashEvent>,
    onLoggedIn: () -> Unit
) {

    LaunchedEffect(key1 = true) {
        effect.collectLatest { event ->
            when(event) {
                SplashEvent.LoggedIn -> {
                    onLoggedIn.invoke()
                }
                SplashEvent.LoggedOut -> {
                    navController.navigate(StartScreen.LoginScreen.route) {
                        popUpTo(0)
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Black),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_sample),
                contentDescription = stringResource(id = R.string.splash_image_description),
                contentScale = ContentScale.Fit,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}