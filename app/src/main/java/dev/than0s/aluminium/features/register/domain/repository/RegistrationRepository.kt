package dev.than0s.aluminium.features.register.domain.repository

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.data_class.Failure
import dev.than0s.aluminium.core.data_class.RegistrationForm
import kotlinx.coroutines.flow.Flow

interface RegistrationRepository {
    suspend fun register(form: RegistrationForm): Either<Failure, Unit>
    suspend fun getRequestsList(): Either<Failure, Flow<List<RegistrationForm>>>
}