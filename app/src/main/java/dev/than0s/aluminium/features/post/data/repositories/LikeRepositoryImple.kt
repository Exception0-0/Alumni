package dev.than0s.aluminium.features.post.data.repositories

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.post.data.data_source.LikeDataSource
import dev.than0s.aluminium.features.post.domain.repository.LikeRepository
import javax.inject.Inject

class LikeRepositoryImple @Inject constructor(val dataSource: LikeDataSource) : LikeRepository {
    override suspend fun addLike(postId: String): Either<Failure, Unit> {
        return try {
            dataSource.addLike(postId)
            Either.Right(Unit)
        } catch (e: Exception) {
            Either.Left(Failure(e.message.toString()))
        }
    }

    override suspend fun removeLike(postId: String): Either<Failure, Unit> {
        return try {
            dataSource.removeLike(postId)
            Either.Right(Unit)
        } catch (e: Exception) {
            Either.Left(Failure(e.message.toString()))
        }
    }

    override suspend fun hasUserLiked(postId: String): Either<Failure, Boolean> {
        return try {
            Either.Right(dataSource.hasUserLiked(postId))
        } catch (e: Exception) {
            Either.Left(Failure(e.message.toString()))
        }
    }

}