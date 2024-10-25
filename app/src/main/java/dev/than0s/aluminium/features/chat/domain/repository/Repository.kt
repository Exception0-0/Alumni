package dev.than0s.aluminium.features.chat.domain.repository

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.chat.domain.data_class.Chat
import dev.than0s.aluminium.features.chat.domain.data_class.User
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun addChat(chat: Chat): Either<Failure, Unit>
    suspend fun removeChat(chat: Chat): Either<Failure, Unit>
    suspend fun getChatFlow(receiverId: String): Either<Failure, Flow<List<Chat>>>
    suspend fun getCurrentChatList(): Either<Failure, List<User>>
}