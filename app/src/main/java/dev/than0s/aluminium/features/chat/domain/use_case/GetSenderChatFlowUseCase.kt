package dev.than0s.aluminium.features.chat.domain.use_case

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.chat.domain.data_class.Chat
import dev.than0s.aluminium.features.chat.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSenderChatFlowUseCase @Inject constructor(
    private val repository: Repository
) : UseCase<String, Flow<List<Chat>>> {
    override suspend fun invoke(receiverID: String): Either<Failure, Flow<List<Chat>>> {
        return repository.getSenderChatFlow(receiverID)
    }
}