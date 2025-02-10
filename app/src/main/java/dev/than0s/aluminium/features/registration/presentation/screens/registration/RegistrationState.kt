package dev.than0s.aluminium.features.registration.presentation.screens.registration

import dev.than0s.aluminium.core.domain.error.PreferredError
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm

data class RegistrationState(
    val registrationForm: RegistrationForm = RegistrationForm(),
    val isLoading: Boolean = false,
    val courseExpanded: Boolean = false,
    val roleExpanded: Boolean = false,
    val formIndex: Int = 0,
    val collageIdError: PreferredError? = null,
    val firstNameError: PreferredError? = null,
    val middleNameError: PreferredError? = null,
    val lastNameError: PreferredError? = null,
    val emailError: PreferredError? = null,
    val batchFromError: PreferredError? = null,
    val batchToError: PreferredError? = null,
    val courseError: PreferredError? = null,
)
