package dev.than0s.aluminium.features.settings.data.repositories

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.data_class.User
import dev.than0s.aluminium.features.settings.data.data_source.AccountDataSource
import dev.than0s.aluminium.features.settings.domain.repository.AccountRepository
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepositoryImple @Inject constructor(private val dataSource: AccountDataSource) :
    AccountRepository {

    override val hasUser: Either<ServerException, Boolean>
        get() = try {
            Either.Right(dataSource.hasUser)
        } catch (e: ServerException) {
            Either.Left(e)
        }

    override suspend fun signOut(): Either<ServerException, Unit> {
        try {
            dataSource.signOut()
            return Either.Right(Unit)
        } catch (e: ServerException) {
            return Either.Left(e)
        }
    }

    override suspend fun deleteAccount(): Either<ServerException, Unit> {
        try {
            dataSource.deleteAccount()
            return Either.Right(Unit)
        } catch (e: ServerException) {
            return Either.Left(e)
        }
    }
}