package dev.than0s.aluminium.features.profile.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.profile.domain.data_class.User
import dev.than0s.aluminium.features.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class SetProfileUseCase @Inject constructor(private val repository: ProfileRepository) :
    UseCase<User, Unit> {
    override suspend fun invoke(param: User): Either<Failure, Unit> {
        return repository.setUserProfile(param)
    }
}