package dev.than0s.aluminium.features.profile.presentation.screens.profile

import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.domain.error.Error

data class ProfileState(
    val user: User = User(),
    val dialogUser: User = User(),
    val isLoading: Boolean = false,
    val isUpdating: Boolean = false,
    val updateProfileDialog: Boolean = false,
    val tabSelection: Int = 0,
    val firstNameError: Error? = null,
    val lastNameError: Error? = null,
    val bioError: Error? = null,
    val profileImageError: Error? = null,
    val coverImageError: Error? = null,
)