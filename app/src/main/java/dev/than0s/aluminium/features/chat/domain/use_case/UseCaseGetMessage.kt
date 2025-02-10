package dev.than0s.aluminium.features.chat.domain.use_case

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.features.chat.domain.data_class.ChatMessage
import dev.than0s.aluminium.features.chat.domain.repository.RepositoryChat
import javax.inject.Inject

class UseCaseGetMessage @Inject constructor(
    private val repository: RepositoryChat
) {
    suspend operator fun invoke(receiverId: String, messageId: String): Resource<ChatMessage> {
        return repository.getMessage(
            receiverId = receiverId,
            messageId = messageId
        )
    }
}