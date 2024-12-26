package dev.than0s.aluminium.features.profile.data.repositories

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.core.data.remote.error.ServerException
import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.features.profile.data.remote.ProfileDataSource
import dev.than0s.aluminium.core.domain.data_class.AboutInfo
import dev.than0s.aluminium.features.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImple @Inject constructor(private val dataSource: ProfileDataSource) :
    ProfileRepository {

    override suspend fun setUserProfile(profile: User): SimpleResource {
        return try {
            dataSource.setUserProfile(profile)
            Resource.Success(Unit)
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }

    override suspend fun getAboutInfo(userId: String): Resource<AboutInfo> {
        return try {
            Resource.Success(dataSource.getAboutInfo(userId))
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }

    override suspend fun getUserProfile(userId: String): Resource<User> {
        return try {
            Resource.Success(dataSource.getUserProfile(userId))
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }
}