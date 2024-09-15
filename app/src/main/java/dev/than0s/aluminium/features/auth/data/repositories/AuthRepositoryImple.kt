package dev.than0s.aluminium.features.auth.data.repositories

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.auth.data.data_source.AuthDataSource
import dev.than0s.aluminium.features.auth.domain.repository.AuthRepository
import dev.than0s.mydiary.core.error.ServerException
import javax.inject.Inject

class AuthRepositoryImple @Inject constructor(private val dataSource: AuthDataSource) :
    AuthRepository {
    override suspend fun signIn(email: String, password: String): Either<Failure, Unit> {
        try {
            dataSource.signIn(email, password)
            return Either.Right(Unit)
        } catch (e: ServerException) {
            return Either.Left(Failure(e.message))
        }
    }

    override suspend fun signUp(email: String, password: String): Either<Failure, Unit> {
        try {
            dataSource.signUp(email, password)
            return Either.Right(Unit)
        } catch (e: ServerException) {
            return Either.Left(Failure(e.message))
        }
    }

    override suspend fun forgetPassword(email: String): Either<Failure, Unit> {
        try {
            dataSource.forgetPassword(email)
            return Either.Right(Unit)
        } catch (e: ServerException) {
            return Either.Left(Failure(e.message))
        }
    }

    override suspend fun signOut(): Either<Failure, Unit> {
        try {
            dataSource.signOut()
            return Either.Right(Unit)
        } catch (e: ServerException) {
            return Either.Left(Failure(e.message))
        }
    }
}