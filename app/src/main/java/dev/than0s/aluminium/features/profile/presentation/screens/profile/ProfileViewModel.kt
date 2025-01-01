package dev.than0s.aluminium.features.profile.presentation.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.domain.use_case.GetUserUseCase
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.core.presentation.utils.SnackbarController
import dev.than0s.aluminium.core.presentation.utils.SnackbarEvent
import dev.than0s.aluminium.core.presentation.utils.UiText
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val profileUseCase: GetUserUseCase,
) : ViewModel() {
    val profileScreenArgs = savedStateHandle.toRoute<Screen.ProfileScreen>()
    var screenState by mutableStateOf(ProfileState())

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            screenState = screenState.copy(isLoading = true)
            when (val result = profileUseCase(profileScreenArgs.userId)) {
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

    private fun onTabChanged(tabIndex: Int) {
        screenState = screenState.copy(tabRowSelectedIndex = tabIndex)
    }

    fun onEvent(event: ProfileEvents) {
        when (event) {
            is ProfileEvents.OnLoadProfile -> {
                loadProfile()
            }

            is ProfileEvents.OnTabChanged -> {
                onTabChanged(event.tabIndex)
            }
        }
    }
}