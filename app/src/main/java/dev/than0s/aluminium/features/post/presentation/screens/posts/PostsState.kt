package dev.than0s.aluminium.features.post.presentation.screens.posts

import android.net.Uri
import dev.than0s.aluminium.core.domain.data_class.Post

data class PostsState(
    val isLoading: Boolean = false,
    val isDeleting: Boolean = false,
    val fullScreenImage: Uri? = null,
    val deletePostId: String? = null,
    val postList: List<Post> = emptyList(),
)