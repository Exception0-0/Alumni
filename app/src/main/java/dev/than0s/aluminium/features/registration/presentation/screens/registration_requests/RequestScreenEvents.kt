package dev.than0s.aluminium.features.registration.presentation.screens.registration_requests

import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm

sealed class RequestScreenEvents {
    data class OnAcceptClick(val form: RegistrationForm) : RequestScreenEvents()
    data class OnRejectClick(val form: RegistrationForm) : RequestScreenEvents()
    data class OnShowDialog(val formId: String, val accepted: Boolean) : RequestScreenEvents()
    data class OnShowIdCard(val formId: String) : RequestScreenEvents()
    data object OnHideIdCard : RequestScreenEvents()
    data object OnDismissDialog : RequestScreenEvents()
    data object OnLoad : RequestScreenEvents()
}