package dev.than0s.aluminium.features.profile.presentation.screens.about

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.presentation.utils.SnackbarController
import dev.than0s.aluminium.core.presentation.utils.SnackbarEvent
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.features.profile.domain.use_cases.GetAboutInfoUseCase
import dev.than0s.aluminium.features.profile.presentation.screens.util.ProfileTabScreen
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAboutInfoUseCase: GetAboutInfoUseCase,
) : ViewModel() {

    private val aboutScreenArgs = savedStateHandle.toRoute<ProfileTabScreen.AboutScreen>()
    var screenState by mutableStateOf(AboutState())

    init {
        loadAboutInfo()
    }

    private fun loadAboutInfo() {
        viewModelScope.launch {
            screenState = screenState.copy(isLoading = true)
            when (val result = getAboutInfoUseCase(aboutScreenArgs.userId)) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Success -> {
                    screenState = screenState.copy(
                        aboutInfo = result.data!!
                    )
                }
            }
            screenState = screenState.copy(isLoading = false)
        }
    }

    fun onEvent(event: AboutEvents) {
        when (event) {
            is AboutEvents.LoadAboutInfo -> {
                loadAboutInfo()
            }
        }
    }
}