package dev.than0s.aluminium.features.post.data.repositories

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.data.data_source.PostDataSource
import dev.than0s.aluminium.features.post.domain.data_class.Comment
import dev.than0s.aluminium.features.post.domain.data_class.User
import dev.than0s.aluminium.features.post.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepositoryImple @Inject constructor(private val dataSource: PostDataSource) :
    PostRepository {

    override suspend fun addPost(post: Post): Either<Failure, Unit> {
        return try {
            dataSource.addPost(post)
            Either.Right(Unit)
        } catch (e: Exception) {
            Either.Left(Failure(e.message.toString()))
        }
    }

    override suspend fun deletePost(postId: String): Either<Failure, Unit> {
        return try {
            dataSource.deletePost(postId)
            Either.Right(Unit)
        } catch (e: Exception) {
            Either.Left(Failure(e.message.toString()))
        }
    }

    override suspend fun getPosts(): Either<Failure, List<Post>> {
        return try {
            Either.Right(dataSource.getPosts())
        } catch (e: Exception) {
            Either.Left(Failure(e.message.toString()))
        }
    }

    override suspend fun getCurrentUserPosts(): Either<Failure, List<Post>> {
        return try {
            Either.Right(dataSource.getCurrentUserPosts())
        } catch (e: Exception) {
            Either.Left(Failure(e.message.toString()))
        }
    }

    override suspend fun getUserProfile(userId: String): Either<Failure, User> {
        return try {
            Either.Right(dataSource.getUserProfile(userId))
        } catch (e: Exception) {
            Either.Left(Failure(e.message.toString()))
        }
    }
}