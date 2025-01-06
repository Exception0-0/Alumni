package dev.than0s.aluminium.features.registration.domain.repository

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm

interface RegistrationRepository {
    suspend fun addRegistrationRequest(form: RegistrationForm): SimpleResource
    suspend fun getRegistrationRequests(): Resource<List<RegistrationForm>>
    suspend fun acceptRegistrationRequest(registrationRequestId: String): SimpleResource
    suspend fun rejectRegistrationRequest(registrationRequestId: String): SimpleResource
}