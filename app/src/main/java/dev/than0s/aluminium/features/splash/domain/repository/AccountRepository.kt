package dev.than0s.aluminium.features.splash.domain.repository

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.mydiary.core.error.ServerException

interface AccountRepository {
    val hasUser: Either<Failure, Boolean>
}