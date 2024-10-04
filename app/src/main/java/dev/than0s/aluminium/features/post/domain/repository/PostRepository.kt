package dev.than0s.aluminium.features.post.domain.repository

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.post.domain.data_class.Comment
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.data_class.User
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun addPost(post: Post): Either<Failure, Unit>
    suspend fun deletePost(postId: String): Either<Failure, Unit>
    suspend fun getPosts(userId: String?): Either<Failure, List<Post>>
    suspend fun getUserProfile(userId: String): Either<Failure, User>
}