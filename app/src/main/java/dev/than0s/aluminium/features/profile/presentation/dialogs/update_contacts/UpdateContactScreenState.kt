package dev.than0s.aluminium.features.profile.presentation.dialogs.update_contacts

import dev.than0s.aluminium.core.domain.data_class.ContactInfo
import dev.than0s.aluminium.core.domain.error.PreferredError

data class UpdateContactScreenState(
    val contactInfo: ContactInfo = ContactInfo(),
    val isLoading: Boolean = false,
    val isUpdating: Boolean = false,
    val emailError: PreferredError? = null,
    val mobileError: PreferredError? = null,
    val socialHandleError: PreferredError? = null,
)
