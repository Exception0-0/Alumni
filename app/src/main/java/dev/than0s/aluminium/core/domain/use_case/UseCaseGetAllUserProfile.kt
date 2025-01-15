package dev.than0s.aluminium.core.domain.use_case

import dev.than0s.aluminium.features.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class UseCaseGetAllUserProfile @Inject constructor(val repository: ProfileRepository) {
    suspend operator fun invoke() = repository.getAllUserProfile()
}