package dev.than0s.aluminium.features.post.data.repositories

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.core.data.remote.error.ServerException
import dev.than0s.aluminium.core.domain.data_class.Like
import dev.than0s.aluminium.features.post.data.data_source.LikeDataSource
import dev.than0s.aluminium.features.post.domain.repository.LikeRepository
import javax.inject.Inject

class LikeRepositoryImple @Inject constructor(
    private val likeRemote: LikeDataSource,
) : LikeRepository {
    override suspend fun addLike(like: Like): SimpleResource {
        return try {
            likeRemote.addLike(like)
            Resource.Success(Unit)
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }

    override suspend fun removeLike(like: Like): SimpleResource {
        return try {
            likeRemote.removeLike(like)
            Resource.Success(Unit)
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }

    override suspend fun getCurrentUserLikeStatus(postId: String): Resource<Like?> {
        return try {
            Resource.Success(likeRemote.getCurrentUserLikeStatus(postId))
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }
}