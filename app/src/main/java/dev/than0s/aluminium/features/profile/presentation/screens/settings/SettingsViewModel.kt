package dev.than0s.aluminium.features.profile.presentation.screens.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.domain.use_case.SignOutUseCase
import dev.than0s.aluminium.core.presentation.utils.SnackbarAction
import dev.than0s.aluminium.core.presentation.utils.SnackbarController
import dev.than0s.aluminium.core.presentation.utils.SnackbarEvent
import dev.than0s.aluminium.core.presentation.utils.UiText
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {
    var screenState by mutableStateOf(StateSettingsScreen())

    private fun onSignOut(
        restartApp: () -> Unit
    ) {
        viewModelScope.launch {
            when (val result = signOutUseCase()) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError(),
                            action = SnackbarAction(
                                name = UiText.StringResource(R.string.try_again),
                                action = {
                                    onSignOut(restartApp = restartApp)
                                }
                            )
                        )
                    )
                }

                is Resource.Success -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = UiText.StringResource(R.string.sign_out_successfully),
                        )
                    )
                    dismissLogoutDialog()
                    restartApp()
                }
            }
        }
    }

    private fun showLogoutDialog() {
        screenState = screenState.copy(
            isLogoutDialogShown = true
        )
    }

    private fun dismissLogoutDialog() {
        screenState = screenState.copy(
            isLogoutDialogShown = false
        )
    }

    private fun showAboutDialog() {
        screenState = screenState.copy(
            isAboutDialogShown = true
        )
    }

    private fun dismissAboutDialog() {
        screenState = screenState.copy(
            isAboutDialogShown = false
        )
    }

    fun onEvent(event: SettingsEvents) {
        when (event) {
            is SettingsEvents.OnSignOut -> {
                onSignOut(
                    restartApp = event.restartApp
                )
            }

            is SettingsEvents.ShowLogoutDialog -> {
                showLogoutDialog()
            }

            is SettingsEvents.DismissLogoutDialog -> {
                dismissLogoutDialog()
            }

            SettingsEvents.DismissAboutDialog -> dismissAboutDialog()
            SettingsEvents.ShowAboutDialog -> showAboutDialog()
        }
    }
}
