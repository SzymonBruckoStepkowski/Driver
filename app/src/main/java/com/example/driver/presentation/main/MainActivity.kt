package com.example.driver.presentation.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.driver.presentation.main.home.HomeScreen
import com.example.driver.presentation.ui.theme.DriverTheme
import com.example.driver.common.Constants
import com.example.driver.domain.helper.AccountLogoutHelper
import com.example.driver.domain.provider.LocationProvider
import com.example.driver.presentation.BottomNavItem
import com.example.driver.presentation.MainScreen
import com.example.driver.presentation.main.add_edit.AddEditScreen
import com.example.driver.presentation.main.add_edit.AddEditViewModel
import com.example.driver.presentation.main.home.HomeViewModel
import com.example.driver.presentation.main.profile.ProfileScreen
import com.example.driver.presentation.main.profile.ProfileViewModel
import com.example.driver.presentation.main.report_list.ReportListScreen
import com.example.driver.presentation.main.report_list.ReportListViewModel
import com.example.driver.presentation.start.StartActivity
import com.example.driver.presentation.ui.theme.Black
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var accountLogoutHelper: AccountLogoutHelper

    @Inject
    lateinit var locationProvider: LocationProvider

    private val requestFineLocationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                locationProvider.startLocationUpdates()
            }
        }

    private val onLocationChecked: () -> Unit = {
        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestFineLocationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        handleLogoutEvent()
        setContent {
            DriverTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = { BottomNavigation(navController = navController) }
                ) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
                        navController = navController,
                        startDestination = MainScreen.HomeScreen.route
                    ) {
                        composable(
                            route = MainScreen.HomeScreen.route
                        ) {
                            val vm = hiltViewModel<HomeViewModel>()
                            HomeScreen(
                                navController = navController,
                                state = vm.state.value,
                                onEvent = vm::onEvent
                            )
                        }
                        composable(
                            route = MainScreen.ReportListScreen.route
                        ) {
                            val vm = hiltViewModel<ReportListViewModel>()
                            ReportListScreen(
                                navController = navController,
                                state = vm.state.value,
                                onEvent = vm::onEvent
                            )
                        }
                        composable(
                            route = MainScreen.ProfileScreen.route
                        ) {
                            val vm = hiltViewModel<ProfileViewModel>()
                            ProfileScreen(
                                state = vm.state.value,
                                onEvent = vm::onEvent,
                                effect = vm.effect
                            )
                        }
                        composable(
                            route = MainScreen.AddEditScreen.route +
                                    "?${Constants.PARAM_REPORT_ID}={${Constants.PARAM_REPORT_ID}}&" +
                                    "${Constants.PARAM_OPERATION_TYPE}={${Constants.PARAM_OPERATION_TYPE}}",
                            arguments = listOf(
                                navArgument(Constants.PARAM_REPORT_ID) {
                                    nullable = true
                                    type = NavType.StringType
                                },
                                navArgument(Constants.PARAM_OPERATION_TYPE) {
                                    nullable = true
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            val vm = hiltViewModel<AddEditViewModel>()
                            AddEditScreen(
                                navController = navController,
                                state = vm.state.value,
                                onEvent = vm::onEvent,
                                effect = vm.effect,
                                onLocationChecked = onLocationChecked
                            )
                        }
                    }
                }
            }
        }
    }

    private fun handleLogoutEvent() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                accountLogoutHelper.logoutEvent.collectLatest {
                    startActivity(Intent(this@MainActivity, StartActivity::class.java))
                    finish()
                }
            }
        }
    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Reports,
        BottomNavItem.Profile
    )
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title.asString()) },
                label = { Text(text = item.title.asString(), fontSize = 9.sp) },
                selectedContentColor = Black,
                unselectedContentColor = Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}
