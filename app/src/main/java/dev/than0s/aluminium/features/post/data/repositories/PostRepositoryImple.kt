package dev.than0s.aluminium.features.post.data.repositories

import android.net.Uri
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.data.data_source.PostDataSource
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

    override suspend fun getPost(id: String): Either<Failure, Post> {
        return try {
            Either.Right(dataSource.getPost(id))
        } catch (e: Exception) {
            Either.Left(Failure(e.message.toString()))
        }
    }

    override suspend fun addPostFile(uri: Uri, id: String): Either<Failure, Unit> {
        return try {
            dataSource.addPostFile(uri, id);
            Either.Right(Unit)
        } catch (e: Exception) {
            Either.Left(Failure(e.message.toString()))
        }
    }

    override suspend fun deletePostFile(id: String): Either<Failure, Unit> {
        try {
            dataSource.deletePostFile(id)
            return Either.Right(Unit)
        } catch (e: Exception) {
            return Either.Left(Failure(e.message.toString()))
        }
    }

    override suspend fun getPostFile(id: String): Either<Failure, Uri> {
        return try {
            return Either.Right(dataSource.getPostFile(id))
        } catch (e: Exception) {
            Either.Left(Failure(e.message.toString()))
        }
    }

    override suspend fun getMyPostFlow(): Either<Failure, Flow<List<Post>>> {
        return try {
            Either.Right(dataSource.getMyPostFlow())
        } catch (e: Exception) {
            Either.Left(Failure(e.message.toString()))
        }
    }

    override suspend fun getAllPostFlow(): Either<Failure, Flow<List<Post>>> {
        return try {
            Either.Right(dataSource.getAllPostFlow())
        } catch (e: Exception) {
            Either.Left(Failure(e.message.toString()))
        }
    }
}