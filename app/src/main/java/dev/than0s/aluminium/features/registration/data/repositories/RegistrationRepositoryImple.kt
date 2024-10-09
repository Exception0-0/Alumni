package dev.than0s.aluminium.features.registration.data.repositories

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm
import dev.than0s.aluminium.features.registration.data.data_source.RegisterDataSource
import dev.than0s.aluminium.features.registration.domain.repository.RegistrationRepository
import dev.than0s.mydiary.core.error.ServerException
import javax.inject.Inject

class RegistrationRepositoryImple @Inject constructor(private val dataSource: RegisterDataSource) :
    RegistrationRepository {
    override suspend fun setRegistration(form: RegistrationForm): Either<Failure, Unit> {
        return try {
            dataSource.setRegistration(form)
            Either.Right(Unit)
        } catch (e: ServerException) {
            Either.Left(Failure(e.message))
        }
    }

    override suspend fun registrationList(): Either<Failure, List<RegistrationForm>> {
        return try {
            Either.Right(dataSource.registrationList())
        } catch (e: ServerException) {
            Either.Left(Failure(e.message))
        }
    }
}