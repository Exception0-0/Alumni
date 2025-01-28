package dev.than0s.aluminium.features.auth.domain.data_class

import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.domain.error.PreferredError

data class ForgetPasswordResult(
    val emailError: PreferredError? = null,
    val result: SimpleResource? = null,
)
