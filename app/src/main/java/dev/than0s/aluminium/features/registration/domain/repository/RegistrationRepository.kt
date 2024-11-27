package dev.than0s.aluminium.features.registration.domain.repository

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm
import kotlinx.coroutines.flow.Flow

interface RegistrationRepository {
    suspend fun setRegistration(form: RegistrationForm): Either<Failure, Unit>
    suspend fun registrationList(): Either<Failure, Flow<List<RegistrationForm>>>
}