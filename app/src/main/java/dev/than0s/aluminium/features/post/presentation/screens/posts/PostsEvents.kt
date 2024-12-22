package dev.than0s.aluminium.features.post.presentation.screens.posts

sealed class PostsEvents {
    data class OnLikeClick(val postId: String, val hasLike: Boolean) : PostsEvents()
    data class OnPostDeleteClick(val postId: String) : PostsEvents()
    data class GetUser(val userId: String) : PostsEvents()
    data class GetLike(val postId: String) : PostsEvents()
    data object LoadPosts : PostsEvents()
}