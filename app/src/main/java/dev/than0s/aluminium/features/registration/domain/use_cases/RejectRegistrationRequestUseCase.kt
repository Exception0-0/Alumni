package dev.than0s.aluminium.features.registration.domain.use_cases

import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.features.registration.domain.repository.RegistrationRepository
import javax.inject.Inject

class RejectRegistrationRequestUseCase @Inject constructor(private val repository: RegistrationRepository) {
    suspend operator fun invoke(registrationRequestId: String): SimpleResource {
        return repository.rejectRegistrationRequest(registrationRequestId)
    }
}