package dev.than0s.aluminium.features.registration.data.repositories

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.data.remote.error.ServerException
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.features.registration.data.remote.RegisterDataSource
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm
import dev.than0s.aluminium.features.registration.domain.repository.RegistrationRepository
import javax.inject.Inject

class RegistrationRepositoryImple @Inject constructor(private val dataSource: RegisterDataSource) :
    RegistrationRepository {
    override suspend fun addRegistrationRequest(form: RegistrationForm): SimpleResource {
        return try {
            dataSource.addRegistrationRequest(form)
            Resource.Success(Unit)
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }

    override suspend fun getRegistrationRequests(): Resource<List<RegistrationForm>> {
        return try {
            Resource.Success(dataSource.getRegistrationRequests())
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }

    override suspend fun acceptRegistrationRequest(registrationRequestId: String): SimpleResource {
        return try {
            dataSource.acceptRegistrationRequest(registrationRequestId)
            Resource.Success(Unit)
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }

    override suspend fun rejectRegistrationRequest(registrationRequestId: String): SimpleResource {
        return try {
            dataSource.rejectRegistrationRequest(registrationRequestId)
            Resource.Success(Unit)
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }
}