package dev.than0s.aluminium.features.post.data.repositories

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.post.data.data_source.CommentDataSource
import dev.than0s.aluminium.features.post.domain.data_class.Comment
import dev.than0s.aluminium.features.post.domain.repository.CommentRepository
import javax.inject.Inject

class CommentRepositoryImple @Inject constructor(private val dataSource: CommentDataSource) :
    CommentRepository {
    override suspend fun addComment(comment: Comment): Either<Failure, Unit> {
        return try {
            dataSource.addComment(comment)
            Either.Right(Unit)
        } catch (e: Exception) {
            Either.Left(Failure(e.message.toString()))
        }
    }

    override suspend fun removeComment(postId: String, commentId: String): Either<Failure, Unit> {
        return try {
            dataSource.removeComment(postId, commentId)
            Either.Right(Unit)
        } catch (e: Exception) {
            Either.Left(Failure(e.message.toString()))
        }
    }

    override suspend fun getComments(postId: String): Either<Failure, List<Comment>> {
        return try {
            Either.Right(dataSource.getComments(postId))
        } catch (e: Exception) {
            Either.Left(Failure(e.message.toString()))
        }
    }

}