package dev.than0s.aluminium.features.profile.presentation.screens.contact

import dev.than0s.aluminium.core.domain.data_class.ContactInfo

data class ContactState(
    val contactInfo: ContactInfo = ContactInfo(),
    val isLoading: Boolean = false,
)