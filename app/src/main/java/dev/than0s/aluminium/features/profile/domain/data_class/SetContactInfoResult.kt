package dev.than0s.aluminium.features.profile.domain.data_class

import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.data.remote.error.Error

data class SetContactInfoResult(
    val mobileError: Error? = null,
    val emailError: Error? = null,
    val socialHandel: Error? = null,
    val result:SimpleResource? = null,
)
