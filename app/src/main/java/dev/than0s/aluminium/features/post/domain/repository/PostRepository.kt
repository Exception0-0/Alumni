package dev.than0s.aluminium.features.post.domain.repository

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.post.domain.data_class.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun addPost(post: Post): Either<Failure, Unit>
    suspend fun deletePost(id: String): Either<Failure, Unit>
    suspend fun getPostFlow(id: String?): Either<Failure, Flow<List<Post>>>
    suspend fun addLike(id: String): Either<Failure, Unit>
    suspend fun removeLike(id: String): Either<Failure, Unit>
}