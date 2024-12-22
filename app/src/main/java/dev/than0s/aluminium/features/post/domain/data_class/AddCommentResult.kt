package dev.than0s.aluminium.features.post.domain.data_class

import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.data.remote.error.Error

data class AddCommentResult(
    val messageError: Error? = null,
    val result: SimpleResource? = null
)
