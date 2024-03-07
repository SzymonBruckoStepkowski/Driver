package com.example.driver.domain.use_case.validation

import com.example.driver.util.UiText

data class ValidationResult(
    val successful: Boolean = true,
    val errorMessage: UiText? = null
)