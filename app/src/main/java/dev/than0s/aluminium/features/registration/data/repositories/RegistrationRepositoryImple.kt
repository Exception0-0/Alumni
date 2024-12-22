package dev.than0s.aluminium.features.registration.data.repositories

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.UiText
import dev.than0s.aluminium.core.data.remote.error.ServerException
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm
import dev.than0s.aluminium.features.registration.data.remote.RegisterDataSource
import dev.than0s.aluminium.features.registration.domain.repository.RegistrationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegistrationRepositoryImple @Inject constructor(private val dataSource: RegisterDataSource) :
    RegistrationRepository {
    override suspend fun setRegistration(form: RegistrationForm): SimpleResource {
        return try {
            dataSource.setRegistration(form)
            Resource.Success(Unit)
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }

    override suspend fun registrationList(): Resource<List<RegistrationForm>> {
        return try {
            Resource.Success(dataSource.registrationList())
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }
}