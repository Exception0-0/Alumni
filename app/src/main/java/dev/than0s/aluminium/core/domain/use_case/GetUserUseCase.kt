package dev.than0s.aluminium.core.domain.use_case

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.features.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend operator fun invoke(userId: String): Resource<User?> {
        return repository.getUserProfile(userId)
    }
}