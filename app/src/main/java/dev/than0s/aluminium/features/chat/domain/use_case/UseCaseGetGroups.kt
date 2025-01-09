package dev.than0s.aluminium.features.chat.domain.use_case

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.features.chat.domain.data_class.ChatGroup
import dev.than0s.aluminium.features.chat.domain.repository.RepositoryChat
import javax.inject.Inject

class UseCaseGetGroups @Inject constructor(
    private val repository: RepositoryChat
) {
    suspend operator fun invoke(): Resource<List<ChatGroup>> {
        return repository.getGroups()
    }
}