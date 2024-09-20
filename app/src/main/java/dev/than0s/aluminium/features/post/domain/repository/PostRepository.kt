package dev.than0s.aluminium.features.post.domain.repository

import android.net.Uri
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.post.domain.data_class.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun addPost(post: Post): Either<Failure, Unit>
    suspend fun deletePost(id: String): Either<Failure, Unit>
    suspend fun getPost(id: String): Either<Failure, Post>
    suspend fun addPostFile(uri: Uri, id: String): Either<Failure, Unit>
    suspend fun deletePostFile(id: String): Either<Failure, Unit>
    suspend fun getPostFile(id: String): Either<Failure, Uri>
    suspend fun getMyPostFlow(): Either<Failure, Flow<List<Post>>>
    suspend fun getAllPostFlow(): Either<Failure, Flow<List<Post>>>
}