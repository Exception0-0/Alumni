package dev.than0s.aluminium.features.post.presentation.screens.comments

import dev.than0s.aluminium.core.domain.data_class.Comment
import dev.than0s.aluminium.core.domain.error.Error

data class CommentState(
    val comment: Comment = Comment(),
    val deleteCommentId: String? = null,
    val isLoading: Boolean = false,
    val isCommentAdding: Boolean = false,
    val isDeleting: Boolean = false,
    val commentError: Error? = null,
    val commentList: List<Comment> = emptyList()
)