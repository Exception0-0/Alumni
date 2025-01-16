package dev.than0s.aluminium.features.post.presentation.screens.posts

import android.net.Uri

sealed class PostsEvents {
    data class OnLikeClick(val postId: String) : PostsEvents()
    data class GetLike(val postId: String) : PostsEvents()
    data class ShowPostDeleteDialog(val postId: String) : PostsEvents()
    data class ShowFullScreenImage(val uri: Uri) : PostsEvents()
    data object DismissFullScreenImage : PostsEvents()
    data object DeletePost : PostsEvents()
    data object DismissPostDeleteDialog : PostsEvents()
    data object LoadPosts : PostsEvents()
}
