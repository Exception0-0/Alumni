package dev.than0s.aluminium.features.post.data.repositories

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.core.data.remote.error.ServerException
import dev.than0s.aluminium.core.domain.data_class.Post
import dev.than0s.aluminium.features.post.data.data_source.PostDataSource
import dev.than0s.aluminium.features.post.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImple @Inject constructor(private val dataSource: PostDataSource) :
    PostRepository {

    override suspend fun addPost(post: Post): SimpleResource {
        return try {
            dataSource.addPost(post)
            Resource.Success(Unit)
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }

    override suspend fun deletePost(postId: String): SimpleResource {
        return try {
            dataSource.deletePost(postId)
            Resource.Success(Unit)
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }

    override suspend fun getPosts(userId: String?): Resource<List<Post>> {
        return try {
            Resource.Success(dataSource.getPosts(userId))
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }
}