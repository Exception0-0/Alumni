package dev.than0s.aluminium.features.register.data.repositories

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.data_class.Failure
import dev.than0s.aluminium.core.data_class.RegistrationForm
import dev.than0s.aluminium.features.register.data.data_source.RegisterDataSource
import dev.than0s.aluminium.features.register.domain.repository.RegistrationRepository
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegistrationRepositoryImple @Inject constructor(private val dataSource: RegisterDataSource) :
    RegistrationRepository {
    override suspend fun register(form: RegistrationForm): Either<Failure, Unit> {
        return try {
            dataSource.register(form)
            Either.Right(Unit)
        } catch (e: ServerException) {
            Either.Left(Failure(e.message))
        }
    }

    override suspend fun getRequestsList(): Either<Failure, Flow<List<RegistrationForm>>> {
        return try {
            Either.Right(dataSource.requestsList)
        } catch (e: ServerException) {
            Either.Left(Failure(e.message))
        }
    }
}