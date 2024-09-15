package dev.than0s.aluminium.features.post.data.repositories

import android.net.Uri
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.data.data_source.PostDataSource
import dev.than0s.aluminium.features.post.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImple @Inject constructor(private val dataSource: PostDataSource) : PostRepository {

    override suspend fun setPost(post: Post): Either<Failure, Unit> {
        try {
            dataSource.setPost(post)
            return Either.Right(Unit)
        } catch (e: Exception) {
            return Either.Left(Failure(e.message.toString()))
        }
    }

    override suspend fun getPost(id: String): Either<Failure, Post> {
        return try {
            Either.Right(dataSource.getPost(id))
        } catch (e: Exception) {
            Either.Left(Failure(e.message.toString()))
        }
    }

    override suspend fun setPostFile(uri: Uri, id: String): Either<Failure, Unit> {
        return try {
            dataSource.setPostFile(uri, id);
            Either.Right(Unit)
        } catch (e: Exception) {
            Either.Left(Failure(e.message.toString()))
        }
    }

    override suspend fun getPostFile(id: String): Either<Failure, Uri> {
        return try {
            return Either.Right(dataSource.getPostFile(id))
        } catch (e: Exception) {
            Either.Left(Failure(e.message.toString()))
        }
    }
}