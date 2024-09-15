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
        return when (val imageResult = repository.setProfileImage(param.profileImage)) {
            is Either.Right -> {
                when (val docResult = repository.setProfile(param)) {
                    is Either.Left -> Either.Left(docResult.value)
                    is Either.Right -> Either.Right(Unit)
                }
            }

            is Either.Left -> {
                Either.Left(imageResult.value)
            }
        }
    }
}