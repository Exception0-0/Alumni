package dev.than0s.aluminium.features.registration.domain.use_cases

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm
import dev.than0s.aluminium.features.registration.domain.repository.RegistrationRepository
import javax.inject.Inject

class RegistrationRequestListUseCase @Inject constructor(private val repository: RegistrationRepository) {
    suspend operator fun invoke(): Resource<List<RegistrationForm>> {
        return repository.registrationList()
    }
}