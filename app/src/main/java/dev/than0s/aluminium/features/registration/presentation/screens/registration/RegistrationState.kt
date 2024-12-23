package dev.than0s.aluminium.features.registration.presentation.screens.registration

import dev.than0s.aluminium.core.domain.error.Error
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm

data class RegistrationState(
    val registrationForm: RegistrationForm = RegistrationForm(),
    val isLoading: Boolean = false,
    val formIndex: Int = 0,
    val collageIdError: Error? = null,
    val firstNameError: Error? = null,
    val middleNameError: Error? = null,
    val lastNameError: Error? = null,
    val emailError: Error? = null,
    val batchFromError: Error? = null,
    val batchToError: Error? = null,
)
