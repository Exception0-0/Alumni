package dev.than0s.aluminium.features.profile.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.profile.domain.data_class.User
import dev.than0s.aluminium.features.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: ProfileRepository) :
    UseCase<Unit, User?> {
    override suspend fun invoke(param: Unit): Either<Failure, User?> {
        return when (val docResult = repository.getProfile()) {
            is Either.Right -> {
                when (val imageResult = repository.getProfileImage()) {
                    is Either.Right -> Either.Right(docResult.value?.copy(profileImage = imageResult.value))
                    is Either.Left -> Either.Right(docResult.value)
                }
            }

            is Either.Left -> Either.Left(docResult.value)
        }
    }
}