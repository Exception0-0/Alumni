package dev.than0s.aluminium.features.profile.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class GetUserPostsUseCase @Inject constructor(private val repository: ProfileRepository) :
    UseCase<String, List<Post>> {
    override suspend fun invoke(userId: String): Either<Failure, List<Post>> {
        return repository.getUserPosts(userId)
    }
}