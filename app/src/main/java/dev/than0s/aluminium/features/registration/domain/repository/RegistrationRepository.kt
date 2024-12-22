package dev.than0s.aluminium.features.registration.domain.repository

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm

interface RegistrationRepository {
    suspend fun setRegistration(form: RegistrationForm): SimpleResource
    suspend fun registrationList(): Resource<List<RegistrationForm>>
}