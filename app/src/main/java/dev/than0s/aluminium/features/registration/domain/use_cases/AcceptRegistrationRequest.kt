package dev.than0s.aluminium.features.registration.domain.use_cases

import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm
import dev.than0s.aluminium.features.registration.domain.repository.RegistrationRepository
import javax.inject.Inject

class AcceptRegistrationRequest @Inject constructor(private val repository: RegistrationRepository) {
    suspend operator fun invoke(form: RegistrationForm): SimpleResource {
        return repository.setRegistration(
            form.copy(
                status = form.status.copy(
                    approvalStatus = true,
                    accountGeneratedStatus = false,
                )
            )
        )
    }
}