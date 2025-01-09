package dev.than0s.aluminium.features.chat.domain.use_case

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.features.chat.domain.data_class.ChatMessage
import dev.than0s.aluminium.features.chat.domain.repository.RepositoryChat
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UseCaseGetMessages @Inject constructor(
    private val repository: RepositoryChat
) {
    operator fun invoke(groupId: String): Resource<Flow<List<ChatMessage>>> {
        return repository.getMessages(groupId = groupId)
    }
}