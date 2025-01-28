package dev.than0s.aluminium.features.post.domain.data_class

import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.domain.error.PreferredError

data class AddCommentResult(
    val messageError: PreferredError? = null,
    val result: SimpleResource? = null
)
