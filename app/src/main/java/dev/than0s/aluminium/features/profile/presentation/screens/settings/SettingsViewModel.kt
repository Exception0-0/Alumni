package dev.than0s.aluminium.features.profile.presentation.screens.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.Role
import dev.than0s.aluminium.core.SnackbarController
import dev.than0s.aluminium.core.SnackbarEvent
import dev.than0s.aluminium.core.UiText
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.currentUserRole
import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.domain.use_case.GetUserUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val profileUseCase: GetUserUseCase,
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

    fun onEvent(event: SettingsEvents) {
        when (event) {
            is SettingsEvents.LoadProfile -> {
                loadProfile()
            }
        }
    }
}
