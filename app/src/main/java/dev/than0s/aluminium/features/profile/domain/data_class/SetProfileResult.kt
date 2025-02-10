package dev.than0s.aluminium.features.profile.domain.data_class

import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.domain.error.PreferredError

data class SetProfileResult(
    val firstNameError: PreferredError? = null,
    val lastNameError: PreferredError? = null,
    val bioError: PreferredError? = null,
    val profileImageError: PreferredError? = null,
    val coverImageError: PreferredError? = null,
    val result: SimpleResource? = null,
)