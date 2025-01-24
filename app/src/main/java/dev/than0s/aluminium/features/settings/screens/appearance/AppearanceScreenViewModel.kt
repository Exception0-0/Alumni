package dev.than0s.aluminium.features.settings.screens.appearance

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppearanceScreenViewModel @Inject constructor() : ViewModel() {
    var screenState by mutableStateOf(AppearanceScreenState())

    private fun onColorThemeDialogDismissRequest() {
        screenState = screenState.copy(
            colorThemeDialog = false
        )
    }

    private fun onColorThemeDialogShowRequest() {
        screenState = screenState.copy(
            colorThemeDialog = true
        )
    }

    private fun showRoundedCornersDialog() {
        screenState = screenState.copy(
            roundedCornersDialog = true
        )
    }

    private fun dismissRoundedCornersDialog() {
        screenState = screenState.copy(
            roundedCornersDialog = false
        )
    }

    fun onEvent(event: AppearanceScreenEvents) {
        when (event) {
            is AppearanceScreenEvents.OnColorThemeDialogDismissRequest -> {
                onColorThemeDialogDismissRequest()
            }

            is AppearanceScreenEvents.OnColorThemeDialogShowRequest -> {
                onColorThemeDialogShowRequest()
            }

            AppearanceScreenEvents.DismissRoundedCornersDialog -> dismissRoundedCornersDialog()
            AppearanceScreenEvents.ShowRoundedCornersDialog -> showRoundedCornersDialog()
        }
    }
}