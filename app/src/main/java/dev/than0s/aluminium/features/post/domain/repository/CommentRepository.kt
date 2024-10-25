package dev.than0s.aluminium.features.post.domain.repository

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.post.domain.data_class.Comment
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    suspend fun addComment(comment: Comment): Either<Failure, Unit>
    suspend fun removeComment(postId: String, commentId: String): Either<Failure, Unit>
    suspend fun getComments(postId: String): Either<Failure, List<Comment>>
}