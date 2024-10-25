package dev.than0s.aluminium.features.chat.domain.use_case

import com.google.firebase.Timestamp
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.chat.domain.data_class.Chat
import dev.than0s.aluminium.features.chat.domain.repository.Repository
import javax.inject.Inject

class AddChatUseCase @Inject constructor(
    private val repository: Repository
) : UseCase<Chat, Unit> {
    override suspend fun invoke(param: Chat): Either<Failure, Unit> {
        val newChat = param.copy(
            id = System.currentTimeMillis().toString(),
            senderId = currentUserId!!,
            timeStamp = Timestamp.now()
        )
        return repository.addChat(newChat)
    }
}