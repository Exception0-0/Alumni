package dev.than0s.aluminium.features.splash.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.Role
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.currentUserRole
import dev.than0s.aluminium.core.domain.use_case.GetUserUseCase
import dev.than0s.aluminium.core.domain.use_case.UseCaseGetUserStatus
import dev.than0s.aluminium.core.domain.use_case.UseCaseUpdateUserStatusToOnline
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.core.presentation.utils.SnackbarAction
import dev.than0s.aluminium.core.presentation.utils.SnackbarController
import dev.than0s.aluminium.core.presentation.utils.SnackbarEvent
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.core.setCurrentUserId
import dev.than0s.aluminium.core.setCurrentUserRole
import dev.than0s.aluminium.features.profile.domain.use_cases.GetAboutInfoUseCase
import dev.than0s.aluminium.features.splash.domain.use_cases.UseCaseGetCurrentUserId
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAboutInfoUseCase: GetAboutInfoUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val useCaseGetCurrentUserId: UseCaseGetCurrentUserId,
    private val useCaseGetUserStatus: UseCaseGetUserStatus,
    private val useCaseUpdateUserStatusToOnline: UseCaseUpdateUserStatusToOnline
) : ViewModel() {

    private fun loadScreen(
        replaceScreen: (Screen) -> Unit
    ) {
        viewModelScope.launch {
            setCurrentUserId(useCaseGetCurrentUserId.currentUserId)

            when (currentUserId) {
                null -> {
                    replaceScreen(Screen.SignInScreen)
                    setCurrentUserRole(Role.Anonymous)
                }

                else -> {
                    one(replaceScreen = replaceScreen)
                }
            }
        }
    }

    private suspend fun one(
        replaceScreen: (Screen) -> Unit
    ) {
        when (val result = getAboutInfoUseCase(currentUserId!!)) {
            is Resource.Error -> {
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = result.uiText ?: UiText.unknownError(),
                        action = SnackbarAction(
                            name = UiText.StringResource(R.string.try_again),
                            action = {
                                one(replaceScreen = replaceScreen)
                            }
                        )
                    )
                )
            }

            is Resource.Success -> {
                setCurrentUserRole(result.data!!.role)
                when (currentUserRole) {
                    Role.Admin -> {
                        replaceScreen(Screen.RegistrationRequestsScreen)
                    }

                    else -> {
                        two(replaceScreen = replaceScreen)
                    }
                }
            }
        }
    }

    private suspend fun two(
        replaceScreen: (Screen) -> Unit
    ) {
        when (val result = getUserUseCase(currentUserId!!)) {
            is Resource.Error -> {
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = result.uiText ?: UiText.unknownError(),
                        action = SnackbarAction(
                            name = UiText.StringResource(R.string.try_again),
                            action = {
                                two(replaceScreen = replaceScreen)
                            }
                        )
                    )
                )
            }

            is Resource.Success -> {
                when (result.data) {
                    null -> {
                        replaceScreen(Screen.CreateProfileDialog)
                    }

                    else -> {
                        replaceScreen(Screen.HomeScreen())
                        // add task here
                        readUserStatus()
                    }
                }
            }
        }
    }

    private suspend fun readUserStatus() {
        when (val result = useCaseGetUserStatus(currentUserId!!)) {
            is Resource.Error -> {
                SnackbarEvent(
                    message = result.uiText ?: UiText.unknownError(),
                    action = SnackbarAction(
                        name = UiText.StringResource(R.string.try_again),
                        action = {
                            readUserStatus()
                        }
                    )
                )
            }

            is Resource.Success -> {
                result.data!!.collect {
                    if (!it.isOnline) {
                        useCaseUpdateUserStatusToOnline()
                    }
                }
            }
        }
    }

    fun onEvent(event: SplashScreenEvents) {
        when (event) {
            is SplashScreenEvents.OnLoad -> loadScreen(replaceScreen = event.replaceScreen)
        }
    }
}