package dev.than0s.aluminium.features.profile.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.domain.use_case.SignOutUseCase
import dev.than0s.aluminium.core.presentation.utils.SnackbarController
import dev.than0s.aluminium.core.presentation.utils.SnackbarEvent
import dev.than0s.aluminium.core.presentation.utils.UiText
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

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
            is SettingsEvents.OnSignOut -> {
                onSignOut(
                    restartApp = event.restartApp
                )
            }
        }
    }
}
