package dev.than0s.aluminium.features.splash.domain.repository

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.features.splash.domain.data_class.CurrentUser

interface AccountRepository {
    suspend fun getCurrentUser(): Resource<CurrentUser>
    suspend fun hasUserProfileCreated(userId: String): Resource<Boolean>
}