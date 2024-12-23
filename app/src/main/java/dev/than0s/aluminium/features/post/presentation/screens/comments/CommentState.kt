package dev.than0s.aluminium.features.post.presentation.screens.comments

import dev.than0s.aluminium.core.domain.error.Error
import dev.than0s.aluminium.features.post.domain.data_class.Comment

data class CommentState(
    val isLoading: Boolean = false,
    val isCommentAdding: Boolean = false,
    val comment: Comment = Comment(),
    val commentError: Error? = null,
    val commentList: List<Comment> = emptyList()
)