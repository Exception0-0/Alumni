package dev.than0s.aluminium.features.profile.presentation.screens.post

sealed class PostsEvents {
    data class OnLikeClick(val postId: String, val hasLike: Boolean) : PostsEvents()
    data class OnPostClick(val postId: String) : PostsEvents()
    data class GetLike(val postId: String) : PostsEvents()
    data object LoadPosts : PostsEvents()
    data object OnPostDialogDismissRequest : PostsEvents()
}