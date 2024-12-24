package dev.than0s.aluminium.features.post.presentation.screens.post_upload

import dev.than0s.aluminium.core.domain.error.Error
import dev.than0s.aluminium.core.domain.data_class.Post

data class PostStatus(
    val post: Post = Post(),
    val isLoading: Boolean = false,
    val titleError: Error? = null,
    val fileError: Error? = null,
    val descriptionError: Error? = null,
)
