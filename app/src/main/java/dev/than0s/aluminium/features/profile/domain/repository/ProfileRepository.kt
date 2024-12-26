package dev.than0s.aluminium.features.profile.domain.repository

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.domain.data_class.AboutInfo
import dev.than0s.aluminium.core.domain.data_class.User

interface ProfileRepository {
    suspend fun setUserProfile(profile: User): SimpleResource
    suspend fun getUserProfile(userId: String): Resource<User>
    suspend fun getAboutInfo(userId: String): Resource<AboutInfo>
}