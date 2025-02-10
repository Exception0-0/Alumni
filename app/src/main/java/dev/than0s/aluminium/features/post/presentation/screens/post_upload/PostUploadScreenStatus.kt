package dev.than0s.aluminium.features.post.presentation.screens.post_upload

import dev.than0s.aluminium.core.domain.error.PreferredError
import dev.than0s.aluminium.core.domain.data_class.Post

data class PostUploadScreenStatus(
    val post: Post = Post(),
    val isLoading: Boolean = false,
    val titleError: PreferredError? = null,
    val fileError: PreferredError? = null,
    val descriptionError: PreferredError? = null,
)
