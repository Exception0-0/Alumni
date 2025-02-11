package dev.than0s.aluminium.features.splash.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.Role
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.currentUserRole
import dev.than0s.aluminium.core.domain.use_case.UseCaseUpdateUserStatus
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.core.presentation.utils.SnackbarController
import dev.than0s.aluminium.core.presentation.utils.SnackbarEvent
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.core.setCurrentUserId
import dev.than0s.aluminium.core.setCurrentUserRole
import dev.than0s.aluminium.features.splash.domain.use_cases.GetCurrentUserUseCase
import dev.than0s.aluminium.features.splash.domain.use_cases.HasUserProfileCreatedUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getCurrentUser: GetCurrentUserUseCase,
    private val hasUserProfileCreatedUseCase: HasUserProfileCreatedUseCase,
    private val useCaseUpdateUserStatus: UseCaseUpdateUserStatus
) : ViewModel() {
    private fun loadScreen(
        replaceScreen: (Screen) -> Unit
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
                    setCurrentUserRole(result.data.role ?: Role.Anonymous)

                    if (currentUserRole != Role.Anonymous) {
                        useCaseUpdateUserStatus()
                    }

                    when (currentUserRole) {
                        Role.Anonymous -> replaceScreen(Screen.SignInScreen)
                        Role.Admin -> replaceScreen(Screen.RegistrationRequestsScreen)
                        else -> {
                            if (hasUserProfileCreate()) {
                                replaceScreen(Screen.HomeScreen())
                            } else {
                                replaceScreen(Screen.CreateProfileDialog)
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
            is SplashScreenEvents.OnLoad -> loadScreen(replaceScreen = event.replaceScreen)
        }
    }
}