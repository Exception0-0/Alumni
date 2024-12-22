package dev.than0s.aluminium.features.splash.data.repositories

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.Role
import dev.than0s.aluminium.core.UiText
import dev.than0s.aluminium.core.data.remote.error.ServerException
import dev.than0s.aluminium.features.splash.data.data_source.AccountDataSource
import dev.than0s.aluminium.features.splash.domain.data_class.CurrentUser
import dev.than0s.aluminium.features.splash.domain.repository.AccountRepository
import javax.inject.Inject

class AccountRepositoryImple @Inject constructor(private val dataSource: AccountDataSource) :
    AccountRepository {
    override suspend fun getCurrentUser(): Resource<CurrentUser> {
        return try {
            val userId = dataSource.currentUserId
            val role = userId?.let {
                dataSource.getUserRole(it)
            }

            Resource.Success(
                CurrentUser(userId, role?.let { Role.valueOf(it) })
            )
        } catch (e: ServerException) {
            Resource.Error(
                uiText = UiText.DynamicString(e.message)
            )
        }
    }

    override suspend fun hasUserProfileCreated(userId: String): Resource<Boolean> {
        return try {
            Resource.Success(
                dataSource.hasUserProfileCreated(userId)
            )
        } catch (e: ServerException) {
            Resource.Error(
                uiText = UiText.DynamicString(e.message)
            )
        }
    }
}