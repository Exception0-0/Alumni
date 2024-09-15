package dev.than0s.aluminium.features.admin.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.admin.domain.data_class.RequestForm
import dev.than0s.aluminium.features.admin.domain.repositories.RequestRepository
import javax.inject.Inject

class RejectedUserCase @Inject constructor(private val repository: RequestRepository) :
    UseCase<RequestForm, Unit> {
    override suspend fun invoke(param: RequestForm): Either<Failure, Unit> {
        // logic: update Registration form status
        val updatedParam = param.copy(
            status = param.status.copy(
                approvalStatus = false,
            )
        )
        return repository.setRequest(updatedParam)
    }
}