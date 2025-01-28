package dev.than0s.aluminium.features.registration.domain.data_class

import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.domain.error.PreferredError

data class SubmitRegistrationResult(
    val emailError: PreferredError? = null,
    val firstNameError: PreferredError? = null,
    val lastNameError: PreferredError? = null,
    val middleNameError: PreferredError? = null,
    val batchFromError: PreferredError? = null,
    val batchToError: PreferredError? = null,
    val rollNoError: PreferredError? = null,
    val courseError: PreferredError? = null,
    val result: SimpleResource? = null
)
