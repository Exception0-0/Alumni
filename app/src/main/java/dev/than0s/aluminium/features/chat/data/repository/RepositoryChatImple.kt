package dev.than0s.aluminium.features.chat.data.repository

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.data.remote.error.ServerException
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.features.chat.data.remote.RemoteChat
import dev.than0s.aluminium.features.chat.domain.data_class.ChatGroup
import dev.than0s.aluminium.features.chat.domain.data_class.ChatMessage
import dev.than0s.aluminium.features.chat.domain.repository.RepositoryChat
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryChatImple @Inject constructor(
    private val remote: RemoteChat
) : RepositoryChat {
    override suspend fun addMessage(groupId: String, message: ChatMessage): SimpleResource {
        return try {
            remote.addMessage(groupId, message)
            Resource.Success(Unit)
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }

    override suspend fun getGroups(): Resource<List<ChatGroup>> {
        return try {
            Resource.Success(remote.getGroups())
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }

    override fun getMessages(groupId: String): Resource<Flow<List<ChatMessage>>> {
        return try {
            Resource.Success(remote.getMessages(groupId))
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }

}