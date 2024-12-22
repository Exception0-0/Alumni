package dev.than0s.aluminium.core.domain.use_case

import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.domain.data_class.Like
import dev.than0s.aluminium.features.post.domain.repository.LikeRepository
import javax.inject.Inject

class RemoveLikeUseCase @Inject constructor(private val repository: LikeRepository) {
    suspend operator fun invoke(like: Like): SimpleResource {
        return repository.removeLike(like)
    }
}