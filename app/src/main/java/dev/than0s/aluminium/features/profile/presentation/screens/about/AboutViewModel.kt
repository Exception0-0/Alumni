package dev.than0s.aluminium.features.profile.presentation.screens.about

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SnackbarController
import dev.than0s.aluminium.core.SnackbarEvent
import dev.than0s.aluminium.core.UiText
import dev.than0s.aluminium.features.profile.domain.data_class.AboutInfo
import dev.than0s.aluminium.features.profile.domain.use_cases.GetAboutInfoUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val getAboutInfoUseCase: GetAboutInfoUseCase,
) : ViewModel() {

    var aboutInfo by mutableStateOf(AboutInfo())
    var screenState by mutableStateOf(AboutState())
    var userId = ""

    private fun loadAboutInfo() {
        viewModelScope.launch {
            screenState = screenState.copy(isLoading = true)
            when (val result = getAboutInfoUseCase(userId)) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Success -> {
                    aboutInfo = result.data!!
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