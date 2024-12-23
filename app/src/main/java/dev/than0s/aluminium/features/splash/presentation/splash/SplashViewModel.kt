package dev.than0s.aluminium.features.splash.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.Role
import dev.than0s.aluminium.core.Screen
import dev.than0s.aluminium.core.SnackbarController
import dev.than0s.aluminium.core.SnackbarEvent
import dev.than0s.aluminium.core.UiText
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.currentUserRole
import dev.than0s.aluminium.core.setCurrentUserId
import dev.than0s.aluminium.core.setCurrentUserRole
import dev.than0s.aluminium.features.splash.domain.data_class.CurrentUser
import dev.than0s.aluminium.features.splash.domain.use_cases.GetCurrentUserUseCase
import dev.than0s.aluminium.features.splash.domain.use_cases.HasUserProfileCreatedUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getCurrentUser: GetCurrentUserUseCase,
    private val hasUserProfileCreatedUseCase: HasUserProfileCreatedUseCase
) : ViewModel() {
    private fun loadScreen(
        popAndOpen: (Screen) -> Unit
    ) {
        viewModelScope.launch {
            when (val result = getCurrentUser()) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Success -> {
                    setCurrentUserId(result.data!!.userId)
                    result.data.role?.let {
                        setCurrentUserRole(it)
                    }

                    when (currentUserRole) {
                        null -> popAndOpen(Screen.SignInScreen)
                        Role.Admin -> popAndOpen(Screen.RegistrationRequestsScreen)
                        else -> {
                            if (hasUserProfileCreate()) {
                                popAndOpen(Screen.PostsScreen())
                            } else {
                                popAndOpen(Screen.CreateProfileScreen)
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun hasUserProfileCreate(): Boolean {
        return when (val result = hasUserProfileCreatedUseCase(currentUserId!!)) {
            is Resource.Error -> {
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = result.uiText ?: UiText.unknownError()
                    )
                )
                false
            }

            is Resource.Success -> {
                result.data!!
            }
        }
    }

    fun onEvent(event: SplashScreenEvents) {
        when (event) {
            is SplashScreenEvents.OnLoad -> loadScreen(popAndOpen = event.popAndOpen)
        }
    }
}