package dev.than0s.aluminium.features.profile.presentation.dialogs.update_profile

import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.domain.error.PreferredError

data class UpdateProfileDialogState(
    val isLoading: Boolean = false,
    val isUpdating: Boolean = false,
    val userProfile: User = User(),
    val firstNameError: PreferredError? = null,
    val lastNameError: PreferredError? = null,
    val bioError: PreferredError? = null,
    val profileImageError: PreferredError? = null,
    val coverImageError: PreferredError? = null,
)
