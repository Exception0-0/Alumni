package dev.than0s.aluminium.features.admin.domain.repositories

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.admin.domain.data_class.RequestForm
import kotlinx.coroutines.flow.Flow

interface RequestRepository {
    val requestList: Either<Failure, Flow<List<RequestForm>>>
    suspend fun setRequest(status: RequestForm): Either<Failure, Unit>
}