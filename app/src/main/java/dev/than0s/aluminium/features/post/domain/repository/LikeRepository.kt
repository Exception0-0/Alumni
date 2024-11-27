package dev.than0s.aluminium.features.post.domain.repository

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure

interface LikeRepository {
    suspend fun addLike(postId: String): Either<Failure, Unit>
    suspend fun removeLike(postId: String): Either<Failure, Unit>
    suspend fun hasUserLiked(postId: String): Either<Failure, Boolean>
}