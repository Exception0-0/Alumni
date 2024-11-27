package dev.than0s.aluminium.features.post.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.data_class.User
import dev.than0s.aluminium.features.post.domain.repository.PostRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(private val repository: PostRepository) :
    UseCase<String, User> {
    override suspend fun invoke(userId: String): Either<Failure, User> {
        return repository.getUserProfile(userId)
    }
}