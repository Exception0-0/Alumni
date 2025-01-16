package dev.than0s.aluminium.features.chat.domain.use_case

import dev.than0s.aluminium.features.chat.domain.repository.RepositoryChat
import javax.inject.Inject

class UseCaseGetGroup @Inject constructor(val repository: RepositoryChat) {
    suspend operator fun invoke(receiverUserId: String) = repository.getGroup(receiverUserId)
}