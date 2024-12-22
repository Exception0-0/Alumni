package dev.than0s.aluminium.features.profile.domain.use_cases

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.features.profile.domain.data_class.ContactInfo
import dev.than0s.aluminium.features.profile.domain.repository.ContactRepository
import javax.inject.Inject

class GetContactInfoUseCase @Inject constructor(private val repository: ContactRepository) {
    suspend operator fun invoke(userId: String): Resource<ContactInfo> {
        return repository.getContactInfo(userId)
    }
}