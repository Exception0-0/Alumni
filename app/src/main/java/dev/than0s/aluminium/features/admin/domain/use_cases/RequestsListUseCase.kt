package dev.than0s.aluminium.features.admin.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.admin.domain.data_class.RequestForm
import dev.than0s.aluminium.features.admin.domain.repositories.RequestRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RequestsListUseCase @Inject constructor(private val repository: RequestRepository) :
    UseCase<Unit, Flow<List<RequestForm>>> {
    override suspend fun invoke(param: Unit): Either<Failure, Flow<List<RequestForm>>> {
        // TODO: add business logic
        return repository.requestList
    }
}