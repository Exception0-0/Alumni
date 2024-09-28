package dev.than0s.aluminium.features.post.data.repositories

import android.net.Uri
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.data.data_source.PostDataSource
import dev.than0s.aluminium.features.post.domain.data_class.User
import dev.than0s.aluminium.features.post.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepositoryImple @Inject constructor(private val dataSource: PostDataSource) :
    PostRepository {

    override suspend fun addPost(post: Post): Either<Failure, Unit> {
        try {
            dataSource.addPost(post)
            return Either.Right(Unit)
        } catch (e: Exception) {
            return Either.Left(Failure(e.message.toString()))
        }
    }

    override suspend fun deletePost(id: String): Either<Failure, Unit> {
        try {
            dataSource.deletePost(id)
            return Either.Right(Unit)
        } catch (e: Exception) {
            return Either.Left(Failure(e.message.toString()))
        }
    }

    override suspend fun getPostFlow(id: String?): Either<Failure, Flow<List<Post>>> {
        return try {
            Either.Right(dataSource.getPostFlow(id))
        } catch (e: Exception) {
            return Either.Left(Failure(e.message.toString()))
        }
    }

    override suspend fun addLike(id: String): Either<Failure, Unit> {
        return try {
            dataSource.addLike(id)
            Either.Right(Unit)
        } catch (e: Exception) {
            Either.Left(Failure(e.message.toString()))
        }
    }

    override suspend fun removeLike(id: String): Either<Failure, Unit> {
        return try {
            dataSource.removeLike(id)
            Either.Right(Unit)
        } catch (e: Exception) {
            Either.Left(Failure(e.message.toString()))
        }
    }
}