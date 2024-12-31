package dev.than0s.aluminium.features.profile.presentation.screens.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.Role
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.currentUserRole
import dev.than0s.aluminium.core.domain.use_case.GetUserUseCase
import dev.than0s.aluminium.core.domain.use_case.SignOutUseCase
import dev.than0s.aluminium.core.presentation.utils.SnackbarController
import dev.than0s.aluminium.core.presentation.utils.SnackbarEvent
import dev.than0s.aluminium.core.presentation.utils.UiText
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val profileUseCase: GetUserUseCase,
    private val signOutUseCase: SignOutUseCase
) :
    ViewModel() {
    var screenState by mutableStateOf(SettingsState())

    init {
        if (currentUserRole != Role.Admin) {
            loadProfile()
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            screenState = screenState.copy(isLoading = true)
            when (val result = profileUseCase(currentUserId!!)) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Success -> {
                    screenState = screenState.copy(
                        user = result.data!!
                    )
                }
            }
            screenState = screenState.copy(isLoading = false)
        }
    }

    private fun onSignOut(
        restartApp: () -> Unit
    ) {
        viewModelScope.launch {
            when (val result = signOutUseCase()) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Success -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = UiText.DynamicString("Signed out successfully")
                        )
                    )
                    restartApp()
                }
            }
        }
    }

    fun onEvent(event: SettingsEvents) {
        when (event) {
            is SettingsEvents.LoadProfile -> {
                loadProfile()
            }

            is SettingsEvents.OnSignOut -> {
                onSignOut(
                    restartApp = event.restartApp
                )
            }
        }
    }
}
