package dev.than0s.aluminium.features.chat.domain.use_case

import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.features.chat.domain.data_class.ChatGroup
import dev.than0s.aluminium.features.chat.domain.repository.RepositoryChat
import javax.inject.Inject

class UseCaseAddGroup @Inject constructor(
    private val repository: RepositoryChat
) {
    suspend operator fun invoke(chatGroup: ChatGroup): SimpleResource {
        return repository.addGroup(chatGroup = chatGroup)
    }
}