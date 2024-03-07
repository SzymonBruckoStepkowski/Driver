package com.example.driver.domain.use_case.validation

import com.example.driver.R
import com.example.driver.util.UiText
import javax.inject.Inject

class ValidateBlankFieldsUseCase @Inject constructor() {

    operator fun invoke(fields: List<String>): ValidationResult {
        val isAnyFieldBlank = fields.any { it.isBlank() }

        if (isAnyFieldBlank) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.validation_blank_fields_error)
            )
        }

        return ValidationResult()
    }
}