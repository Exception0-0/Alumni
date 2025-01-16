package dev.than0s.aluminium.features.chat.domain.use_case

import dev.than0s.aluminium.features.chat.domain.repository.RepositoryChat
import javax.inject.Inject

class UseCaseGetGroups @Inject constructor(
    private val repository: RepositoryChat
) {
    operator fun invoke() = repository.chatGroups
}