package com.example.driver.presentation.start

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.driver.presentation.StartScreen
import com.example.driver.presentation.main.MainActivity
import com.example.driver.presentation.start.login.LoginScreen
import com.example.driver.presentation.start.login.LoginViewModel
import com.example.driver.presentation.start.splash.SplashScreen
import com.example.driver.presentation.start.splash.SplashViewModel
import com.example.driver.presentation.ui.theme.DriverTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartActivity : ComponentActivity() {

    private val onLoggedIn: () -> Unit = {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DriverTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = StartScreen.SplashScreen.route
                    ) {
                        composable(
                            route = StartScreen.SplashScreen.route
                        ) {
                            val vm = hiltViewModel<SplashViewModel>()
                            SplashScreen(
                                navController = navController,
                                effect = vm.effect,
                                onLoggedIn = onLoggedIn
                            )
                        }
                        composable(
                            route = StartScreen.LoginScreen.route
                        ) {
                            val vm = hiltViewModel<LoginViewModel>()
                            LoginScreen(
                                state = vm.state.value,
                                onEvent = vm::onEvent,
                                effect = vm.effect,
                                onLoggedIn = onLoggedIn
                            )
                        }
                    }
                }
            }
        }
    }
}