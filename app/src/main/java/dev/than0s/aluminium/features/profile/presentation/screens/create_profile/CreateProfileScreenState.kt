package dev.than0s.aluminium.features.profile.presentation.screens.create_profile

import dev.than0s.aluminium.core.domain.error.Error
import dev.than0s.aluminium.core.domain.data_class.User

data class CreateProfileScreenState(
    val isLoading: Boolean = false,
    val userProfile: User = User(),
    val firstNameError: Error? = null,
    val lastNameError: Error? = null,
    val bioError: Error? = null,
    val profileImageError: Error? = null,
    val coverImageError: Error? = null,
)
