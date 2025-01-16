package dev.than0s.aluminium.features.chat.domain.use_case

import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.domain.util.generateUniqueId
import dev.than0s.aluminium.core.presentation.error.TextFieldError
import dev.than0s.aluminium.features.chat.domain.data_class.AddChatMessageResult
import dev.than0s.aluminium.features.chat.domain.data_class.ChatMessage
import dev.than0s.aluminium.features.chat.domain.repository.RepositoryChat
import javax.inject.Inject

class UseCaseAddMessage @Inject constructor(
    private val repository: RepositoryChat
) {
    suspend operator fun invoke(receiverId: String, message: ChatMessage): AddChatMessageResult {
        val messageError = message.let {
            if (message.message.isBlank()) TextFieldError.FieldEmpty
            else null
        }

        if (messageError != null) {
            return AddChatMessageResult(
                messageError = messageError
            )
        }

        return AddChatMessageResult(
            result = repository.addMessage(
                receiverId = receiverId,
                message = message.copy(
                    id = generateUniqueId(),
                    userId = currentUserId!!,
                    timestamp = System.currentTimeMillis()
                )
            )
        )
    }
}