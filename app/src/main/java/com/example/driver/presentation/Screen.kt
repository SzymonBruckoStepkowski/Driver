package com.example.driver.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.driver.R
import com.example.driver.util.UiText

sealed interface Screen

sealed class StartScreen(val route: String): Screen {
    object SplashScreen: StartScreen("splash_screen")
    object LoginScreen: StartScreen("login_screen")
}

sealed class MainScreen(val route: String): Screen {
    object HomeScreen: MainScreen("home_screen")

    object ReportListScreen: MainScreen("report_list_screen")
    object ProfileScreen: MainScreen("profile_screen")

    object AddEditScreen: MainScreen("addedit_screen")
}

sealed class BottomNavItem(val title: UiText, val icon: ImageVector, val route: String) {
    object Home : BottomNavItem(UiText.StringResource(R.string.home_text), Icons.Filled.Home, MainScreen.HomeScreen.route)
    object Reports : BottomNavItem(UiText.StringResource(R.string.reports_text), Icons.Filled.List, MainScreen.ReportListScreen.route)
    object Profile : BottomNavItem(UiText.StringResource(R.string.profile_text), Icons.Filled.Person, MainScreen.ProfileScreen.route)
}