package com.example.driver.data.remote.model

data class UserRequest(
    val username: String? = null,
    val email: String? = null,
    val name: String? = null,
    val avatar: String? = null,
    val vehicle_id: String? = null,
    val trailer_id: String? = null
)