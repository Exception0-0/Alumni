package dev.than0s.aluminium.core.domain.use_case

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.domain.data_class.Like
import dev.than0s.aluminium.features.post.domain.repository.LikeRepository
import javax.inject.Inject

class GetCurrentUserLikeStatusUseCase @Inject constructor(private val repository: LikeRepository) {
    suspend operator fun invoke(postId: String): Resource<Like?> {
        return repository.getCurrentUserLikeStatus(postId)
    }
}