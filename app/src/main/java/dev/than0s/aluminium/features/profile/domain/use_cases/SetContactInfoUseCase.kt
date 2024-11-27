package dev.than0s.aluminium.features.profile.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.profile.domain.data_class.ContactInfo
import dev.than0s.aluminium.features.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class SetContactInfoUseCase @Inject constructor(private val repository: ProfileRepository) :
    UseCase<ContactInfo, Unit> {
    override suspend fun invoke(param: ContactInfo): Either<Failure, Unit> {
        return repository.setContactInfo(param)
    }
}