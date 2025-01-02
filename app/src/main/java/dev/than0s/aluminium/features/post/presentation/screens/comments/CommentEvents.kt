package dev.than0s.aluminium.features.post.presentation.screens.comments

sealed class CommentEvents {
    data class OnCommentChanged(val value: String) : CommentEvents()
    data class ShowCommentDeleteDialog(val postId: String) : CommentEvents()
    data object DeleteComment : CommentEvents()
    data object DismissCommentDeleteDialog : CommentEvents()
    data class GetUser(val userId: String) : CommentEvents()
    data object OnAddCommentClick : CommentEvents()
    data object LoadComments : CommentEvents()
}