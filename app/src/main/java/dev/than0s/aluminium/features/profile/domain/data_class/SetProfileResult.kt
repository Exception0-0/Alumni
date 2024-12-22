package dev.than0s.aluminium.features.profile.domain.data_class

import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.data.remote.error.Error

data class SetProfileResult(
    val firstNameError: Error? = null,
    val lastNameError: Error? = null,
    val bioError: Error? = null,
    val profileImageError: Error? = null,
    val coverImageError: Error? = null,
    val result: SimpleResource? = null,
)