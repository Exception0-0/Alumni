package dev.than0s.aluminium.features.post.domain.repository

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.post.domain.data_class.Comment
import dev.than0s.aluminium.features.post.domain.data_class.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun addPost(post: Post): Either<Failure, Unit>
    suspend fun deletePost(postId: String): Either<Failure, Unit>
    suspend fun getPostFlow(postId: String?): Either<Failure, Flow<List<Post>>>
    suspend fun addLike(postId: String): Either<Failure, Unit>
    suspend fun removeLike(postId: String): Either<Failure, Unit>
    suspend fun addComment(comment: Comment): Either<Failure, Unit>
    suspend fun removeComment(postId: String, commentId: String): Either<Failure, Unit>
    suspend fun getCommentFlow(postId: String): Either<Failure, Flow<List<Comment>>>
}