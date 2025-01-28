package dev.than0s.aluminium.features.registration.domain.data_class

import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.domain.error.Error

data class SubmitRegistrationResult(
    val emailError: Error? = null,
    val firstNameError: Error? = null,
    val lastNameError: Error? = null,
    val middleNameError: Error? = null,
    val batchFromError: Error? = null,
    val batchToError: Error? = null,
    val rollNoError: Error? = null,
    val courseError: Error? = null,
    val result: SimpleResource? = null
)
