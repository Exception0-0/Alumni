package dev.than0s.aluminium.features.auth.data.repositories

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.features.auth.data.data_source.EmailAuthDataSource
import dev.than0s.aluminium.features.auth.domain.repository.EmailAuthRepository
import dev.than0s.mydiary.core.error.ServerException
import javax.inject.Inject

class EmailAuthRepositoryImple @Inject constructor(private val dataSource: EmailAuthDataSource) :
    EmailAuthRepository {
    override suspend fun signIn(email: String, password: String): Either<ServerException, Unit> {
        try {
            dataSource.signIn(email, password)
            return Either.Right(Unit)
        } catch (e: ServerException) {
            return Either.Left(e)
        }
    }

    override suspend fun signUp(email: String, password: String): Either<ServerException, Unit> {
        try {
            dataSource.signUp(email, password)
            return Either.Right(Unit)
        } catch (e: ServerException) {
            return Either.Left(e)
        }
    }

}