package dev.than0s.aluminium.features.profile.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.profile.domain.data_class.User
import dev.than0s.aluminium.features.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: ProfileRepository) :
    UseCase<String, User?> {
    override suspend fun invoke(userId: String): Either<Failure, User?> {
        return repository.getUserProfile(userId)
    }
}