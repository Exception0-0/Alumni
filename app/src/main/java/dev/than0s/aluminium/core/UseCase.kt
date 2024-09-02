package dev.than0s.aluminium.core

import dev.than0s.aluminium.core.data_class.Failure

interface UseCase<Parameters, SuccessType> {
    suspend fun invoke(param: Parameters): Either<Failure, SuccessType>
}