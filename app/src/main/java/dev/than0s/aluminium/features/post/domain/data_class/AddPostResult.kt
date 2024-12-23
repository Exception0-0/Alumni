package dev.than0s.aluminium.features.post.domain.data_class

import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.domain.error.Error

data class AddPostResult(
    val titleError: Error? = null,
    val descriptionError: Error? = null,
    val fileError: Error? = null,
    val result: SimpleResource? = null
)
