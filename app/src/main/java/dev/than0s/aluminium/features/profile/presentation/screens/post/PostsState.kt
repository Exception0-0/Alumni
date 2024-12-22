package dev.than0s.aluminium.features.profile.presentation.screens.post

import dev.than0s.aluminium.core.domain.data_class.Post

data class PostsState(
    val isLoading: Boolean = false,
    val postDialog: String? = null,
    val postList: List<Post> = emptyList()
)
