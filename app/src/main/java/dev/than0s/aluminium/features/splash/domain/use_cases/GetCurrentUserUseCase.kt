package dev.than0s.aluminium.features.splash.domain.use_cases

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.features.splash.domain.data_class.CurrentUser
import dev.than0s.aluminium.features.splash.domain.repository.AccountRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(private val repository: AccountRepository) {
    suspend operator fun invoke(): Resource<CurrentUser> {
        return repository.getCurrentUser()
    }
}