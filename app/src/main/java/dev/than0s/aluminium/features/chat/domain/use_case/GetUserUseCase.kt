package dev.than0s.aluminium.features.chat.domain.use_case

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.chat.domain.data_class.Chat
import dev.than0s.aluminium.features.chat.domain.data_class.User
import dev.than0s.aluminium.features.chat.domain.repository.Repository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: Repository
) : UseCase<String, User> {
    override suspend fun invoke(userId: String): Either<Failure, User> {
        return repository.getUserProfile(userId)
    }
}