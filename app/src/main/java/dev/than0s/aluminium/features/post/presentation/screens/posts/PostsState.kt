package dev.than0s.aluminium.features.post.presentation.screens.posts

import dev.than0s.aluminium.core.domain.data_class.Post

data class PostsState(
    val isLoading: Boolean = false,
    val postList:List<Post> = emptyList(),
)