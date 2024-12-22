package dev.than0s.aluminium.features.post.domain.repository

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.domain.data_class.Like

interface LikeRepository {
    suspend fun addLike(like: Like): SimpleResource
    suspend fun removeLike(like: Like): SimpleResource
    suspend fun getCurrentUserLikeStatus(postId: String): Resource<Like?>
}