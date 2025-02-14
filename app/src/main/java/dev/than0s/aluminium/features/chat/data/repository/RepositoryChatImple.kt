package dev.than0s.aluminium.features.chat.data.repository

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.data.remote.error.ServerException
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.features.chat.data.remote.RemoteChat
import dev.than0s.aluminium.features.chat.domain.data_class.ChatMessage
import dev.than0s.aluminium.features.chat.domain.repository.RepositoryChat
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryChatImple @Inject constructor(
    private val remote: RemoteChat
) : RepositoryChat {
    override val chatGroups
        get() = try {
            Resource.Success(remote.chatGroups)
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }

    override suspend fun addMessage(receiverId: String, message: ChatMessage): SimpleResource {
        return try {
            remote.addMessage(receiverId = receiverId, message = message)
            Resource.Success(Unit)
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }

    override fun getMessages(receiverId: String): Resource<Flow<List<ChatMessage>>> {
        return try {
            Resource.Success(remote.getMessages(receiverId = receiverId))
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }

    override suspend fun getMessage(receiverId: String, messageId: String): Resource<ChatMessage> {
        return try {
            Resource.Success(
                remote.getMessage(
                    receiverId = receiverId,
                    messageId = messageId
                )
            )
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }

    override suspend fun deleteMessage(receiverId: String, messageId: String): SimpleResource {
        return try {
            remote.deleteMessage(
                receiverId = receiverId,
                messageId = messageId
            )
            Resource.Success(Unit)
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }
}