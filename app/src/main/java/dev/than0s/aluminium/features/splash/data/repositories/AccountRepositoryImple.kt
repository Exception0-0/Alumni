package dev.than0s.aluminium.features.splash.data.repositories

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.splash.data.data_source.AccountDataSource
import dev.than0s.aluminium.features.splash.domain.data_class.CurrentUser
import dev.than0s.aluminium.features.splash.domain.repository.AccountRepository
import dev.than0s.mydiary.core.error.ServerException
import javax.inject.Inject

class AccountRepositoryImple @Inject constructor(private val dataSource: AccountDataSource) :
    AccountRepository {
    override suspend fun getCurrentUser(): Either<Failure, CurrentUser> {
        return try {
            val userId = dataSource.currentUserId
            var role: String? = null
            if (userId != null) {
                role = dataSource.getUserRole(userId)
            }
            Either.Right(CurrentUser(userId, role))
        } catch (e: ServerException) {
            Either.Left(Failure(e.message))
        }
    }

}