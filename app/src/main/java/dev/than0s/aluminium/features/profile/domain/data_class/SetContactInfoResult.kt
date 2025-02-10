package dev.than0s.aluminium.features.profile.domain.data_class

import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.domain.error.PreferredError

data class SetContactInfoResult(
    val mobileError: PreferredError? = null,
    val emailError: PreferredError? = null,
    val socialHandel: PreferredError? = null,
    val result:SimpleResource? = null,
)
