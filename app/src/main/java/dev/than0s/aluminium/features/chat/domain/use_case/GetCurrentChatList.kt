package dev.than0s.aluminium.features.chat.domain.use_case

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.chat.domain.data_class.Chat
import dev.than0s.aluminium.features.chat.domain.repository.Repository
import javax.inject.Inject

class GetCurrentChatList @Inject constructor(
    private val repository: Repository
) : UseCase<Unit, List<String>> {
    override suspend fun invoke(param: Unit): Either<Failure, List<String>> {
        return repository.getCurrentChatList()
    }
}