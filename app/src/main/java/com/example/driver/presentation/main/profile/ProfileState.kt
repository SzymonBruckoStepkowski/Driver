package com.example.driver.presentation.main.profile

import com.example.driver.domain.model.User
import com.example.driver.domain.model.Vehicle
import com.example.driver.util.UiText

data class ProfileState(
    val user: User? = null,
    val truckList: List<Vehicle> = emptyList(),
    val trailerList: List<Vehicle> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null,

    val popupControl: Boolean = false
)