package dev.than0s.aluminium.features.profile.presentation.dialogs.update_profile

import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.domain.error.Error

data class UpdateProfileDialogState(
    val isLoading: Boolean = false,
    val isUpdating: Boolean = false,
    val userProfile: User = User(),
    val firstNameError: Error? = null,
    val lastNameError: Error? = null,
    val bioError: Error? = null,
    val profileImageError: Error? = null,
    val coverImageError: Error? = null,
)
