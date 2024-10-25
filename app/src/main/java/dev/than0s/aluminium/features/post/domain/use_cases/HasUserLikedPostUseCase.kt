package dev.than0s.aluminium.features.post.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.post.domain.repository.LikeRepository
import javax.inject.Inject

class HasUserLikedPostUseCase @Inject constructor(private val repository: LikeRepository) :
    UseCase<String, Boolean> {
    override suspend fun invoke(postId: String): Either<Failure, Boolean> {
        return repository.hasUserLiked(postId)
    }
}