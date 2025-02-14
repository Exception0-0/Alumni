package dev.than0s.aluminium.features.chat.domain.use_case

import dev.than0s.aluminium.features.chat.domain.repository.RepositoryChat
import javax.inject.Inject

class UseCaseDeleteMessage @Inject constructor(
    val repository: RepositoryChat
) {
    suspend operator fun invoke(receiverId: String, messageId: String) =
        repository.deleteMessage(receiverId = receiverId, messageId = messageId)
}