package dev.than0s.aluminium.features.profile.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.profile.domain.data_class.AboutInfo
import dev.than0s.aluminium.features.profile.domain.data_class.ContactInfo
import dev.than0s.aluminium.features.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class GetAboutInfoUseCase @Inject constructor(private val repository: ProfileRepository) :
    UseCase<String, AboutInfo> {
    override suspend fun invoke(userId: String): Either<Failure, AboutInfo> {
        return repository.getAboutInfo(userId)
    }
}