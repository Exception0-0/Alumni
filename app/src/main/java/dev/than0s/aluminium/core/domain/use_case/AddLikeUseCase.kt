package dev.than0s.aluminium.core.domain.use_case

import com.google.firebase.Timestamp
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.domain.data_class.Like
import dev.than0s.aluminium.features.post.domain.repository.LikeRepository
import javax.inject.Inject

class AddLikeUseCase @Inject constructor(private val repository: LikeRepository) {
    suspend operator fun invoke(like: Like): SimpleResource {
        return repository.addLike(
            like.copy(
                userId = currentUserId!!,
                timestamp = System.currentTimeMillis()
            )
        )
    }
}