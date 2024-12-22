package dev.than0s.aluminium.features.post.data.repositories

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.UiText
import dev.than0s.aluminium.core.data.remote.error.ServerException
import dev.than0s.aluminium.features.post.data.data_source.CommentDataSource
import dev.than0s.aluminium.features.post.domain.data_class.Comment
import dev.than0s.aluminium.features.post.domain.repository.CommentRepository
import javax.inject.Inject

class CommentRepositoryImple @Inject constructor(private val dataSource: CommentDataSource) :
    CommentRepository {
    override suspend fun addComment(comment: Comment): SimpleResource {
        return try {
            dataSource.addComment(comment)
            Resource.Success(Unit)
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }

    override suspend fun removeComment(comment: Comment): SimpleResource {
        return try {
            dataSource.removeComment(comment)
            Resource.Success(Unit)
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }

    override suspend fun getComments(postId: String): Resource<List<Comment>> {
        return try {
            Resource.Success(dataSource.getComments(postId))
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }

}