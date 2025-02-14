package dev.than0s.aluminium.features.chat.domain.repository

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.features.chat.domain.data_class.ChatGroup
import dev.than0s.aluminium.features.chat.domain.data_class.ChatMessage
import kotlinx.coroutines.flow.Flow

interface RepositoryChat {
    val chatGroups: Resource<Flow<List<ChatGroup>>>
    suspend fun addMessage(receiverId: String, message: ChatMessage): SimpleResource
    fun getMessages(receiverId: String): Resource<Flow<List<ChatMessage>>>
    suspend fun getMessage(receiverId: String, messageId: String): Resource<ChatMessage>
    suspend fun deleteMessage(receiverId: String, messageId: String): SimpleResource
}