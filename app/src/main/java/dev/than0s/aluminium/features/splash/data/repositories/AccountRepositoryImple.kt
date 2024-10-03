package dev.than0s.aluminium.features.splash.data.repositories

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.splash.data.data_source.AccountDataSource
import dev.than0s.aluminium.features.splash.domain.repository.AccountRepository
import dev.than0s.mydiary.core.error.ServerException
import javax.inject.Inject

class AccountRepositoryImple @Inject constructor(private val dataSource: AccountDataSource) :
    AccountRepository {

    override val currentUserId: Either<Failure, String?>
        get() = try {
            Either.Right(dataSource.currentUserId)
        } catch (e: ServerException) {
            Either.Left(Failure(e.message))
        }
}