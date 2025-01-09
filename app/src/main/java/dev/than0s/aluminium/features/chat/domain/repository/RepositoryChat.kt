package dev.than0s.aluminium.features.chat.domain.repository

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.features.chat.domain.data_class.ChatGroup
import dev.than0s.aluminium.features.chat.domain.data_class.ChatMessage
import kotlinx.coroutines.flow.Flow

interface RepositoryChat {
    suspend fun addMessage(groupId: String, message: ChatMessage): SimpleResource
    suspend fun addGroup(chatGroup: ChatGroup): SimpleResource
    suspend fun getGroups(): Resource<List<ChatGroup>>
    fun getMessages(groupId: String): Resource<Flow<List<ChatMessage>>>
}