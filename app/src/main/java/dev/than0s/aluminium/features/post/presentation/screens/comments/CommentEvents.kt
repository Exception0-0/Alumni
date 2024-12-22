package dev.than0s.aluminium.features.post.presentation.screens.comments

import dev.than0s.aluminium.features.post.domain.data_class.Comment

sealed class CommentEvents {
    data class OnCommentChanged(val value: String) : CommentEvents()
    data class OnCommentRemovedClick(val comment: Comment) : CommentEvents()
    data class GetUser(val userId: String) : CommentEvents()
    data object OnAddCommentClick : CommentEvents()
    data object LoadComments : CommentEvents()
}