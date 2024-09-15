package dev.than0s.aluminium.features.admin.data.repostiory

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.admin.data.data_source.RequestDataSource
import dev.than0s.aluminium.features.admin.domain.data_class.RequestForm
import dev.than0s.aluminium.features.admin.domain.repositories.RequestRepository
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RequestRepositoryImple @Inject constructor(private val dataSource: RequestDataSource) :
    RequestRepository {

    override val requestList: Either<Failure, Flow<List<RequestForm>>>
        get() =
            try {
                Either.Right(dataSource.requestList)
            } catch (e: ServerException) {
                Either.Left(Failure(e.message))
            }

    override suspend fun setRequest(status: RequestForm): Either<Failure, Unit> {
        return try {
            dataSource.setRequest(status)
            Either.Right(Unit)
        } catch (e: ServerException) {
            Either.Left(Failure(e.message))
        }
    }
}