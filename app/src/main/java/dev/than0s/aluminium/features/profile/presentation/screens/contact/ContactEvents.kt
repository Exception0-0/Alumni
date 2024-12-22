package dev.than0s.aluminium.features.profile.presentation.screens.contact

sealed class ContactEvents {
    data object LoadContactInfo : ContactEvents()
    data object OnUpdateClick : ContactEvents()
    data object OnUpdateDialogDismissRequest : ContactEvents()
    data class OnEmailChanged(val email: String) : ContactEvents()
    data class OnMobileChanged(val mobile: String) : ContactEvents()
    data class OnSocialChanged(val handel: String) : ContactEvents()
    data object OnContactInfoEditClick : ContactEvents()
}