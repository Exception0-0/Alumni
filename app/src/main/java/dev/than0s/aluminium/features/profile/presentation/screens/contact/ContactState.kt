package dev.than0s.aluminium.features.profile.presentation.screens.contact

import dev.than0s.aluminium.core.domain.error.Error
import dev.than0s.aluminium.core.domain.data_class.ContactInfo

data class ContactState(
    val contactInfo: ContactInfo = ContactInfo(),
    val dialogContactInfo: ContactInfo = ContactInfo(),
    val isLoading: Boolean = false,
    val isUpdating: Boolean = false,
    val updateDialog: Boolean = false,
    val emailError: Error? = null,
    val mobileError: Error? = null,
    val socialHandleError: Error? = null,
)