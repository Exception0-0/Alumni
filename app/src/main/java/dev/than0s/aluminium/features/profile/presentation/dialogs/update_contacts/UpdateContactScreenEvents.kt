package dev.than0s.aluminium.features.profile.presentation.dialogs.update_contacts

sealed class UpdateContactScreenEvents {
    data class OnUpdateClick(val onSuccess: () -> Unit) : UpdateContactScreenEvents()
    data class OnEmailChanged(val email: String) : UpdateContactScreenEvents()
    data class OnMobileChanged(val mobile: String) : UpdateContactScreenEvents()
    data class OnSocialChanged(val handel: String) : UpdateContactScreenEvents()
}