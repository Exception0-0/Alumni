package dev.than0s.aluminium.features.auth.data.repository

import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.features.auth.data.remote.AuthRemote
import dev.than0s.aluminium.features.auth.domain.repository.AuthRepository
import dev.than0s.aluminium.core.data.remote.error.ServerException
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImple @Inject constructor(private val dataSource: AuthRemote) :
    AuthRepository {
    override suspend fun signIn(email: String, password: String): SimpleResource {
        return try {
            dataSource.signIn(email, password)
            Resource.Success(Unit)
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: ServerException) {
            Resource.Error(
                uiText = UiText.DynamicString(e.message)
            )
        }
    }

    override suspend fun signUp(email: String, password: String): SimpleResource {
        return try {
            dataSource.signUp(email, password)
            Resource.Success(Unit)
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: ServerException) {
            Resource.Error(
                uiText = UiText.DynamicString(e.message)
            )
        }
    }

    override suspend fun forgetPassword(email: String): SimpleResource {
        return try {
            dataSource.forgetPassword(email)
            Resource.Success(Unit)
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: ServerException) {
            Resource.Error(
                uiText = UiText.DynamicString(e.message)
            )
        }
    }

    override fun signOut(): SimpleResource {
        return try {
            dataSource.signOut()
            Resource.Success(Unit)
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: ServerException) {
            Resource.Error(
                uiText = UiText.DynamicString(e.message)
            )
        }
    }
}