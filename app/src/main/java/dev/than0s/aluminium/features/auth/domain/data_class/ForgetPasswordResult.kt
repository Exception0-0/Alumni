package dev.than0s.aluminium.features.auth.domain.data_class

import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.domain.error.Error

data class ForgetPasswordResult(
    val emailError: Error? = null,
    val result: SimpleResource? = null,
)
