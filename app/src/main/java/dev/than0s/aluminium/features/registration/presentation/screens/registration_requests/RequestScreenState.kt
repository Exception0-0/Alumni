package dev.than0s.aluminium.features.registration.presentation.screens.registration_requests

import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm

data class RequestScreenState(
    val requestsList: List<RegistrationForm> = emptyList(),
    val isLoading: Boolean = false,
    val requestSelection: Pair<String, Boolean>? = null, // true == accepted false == rejected
    val idCardSelection: String? = null,
    val isDialogLoading: Boolean = false,
)