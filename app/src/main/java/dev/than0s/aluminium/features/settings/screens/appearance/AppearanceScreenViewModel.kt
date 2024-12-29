package dev.than0s.aluminium.features.settings.screens.appearance

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.presentation.ui.ColorTheme
import dev.than0s.aluminium.core.presentation.ui.setCurrentColorTheme
import kotlinx.coroutines.launch
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

    private fun onColorThemeChanged(context: Context, theme: ColorTheme) {
        viewModelScope.launch {
            setCurrentColorTheme(
                context = context,
                theme = theme
            )
        }
    }

    fun onEvent(event: AppearanceScreenEvents) {
        when (event) {
            is AppearanceScreenEvents.OnColorThemeChanged -> {
                onColorThemeChanged(
                    context = event.context,
                    theme = event.theme
                )
            }

            is AppearanceScreenEvents.OnColorThemeDialogDismissRequest -> {
                onColorThemeDialogDismissRequest()
            }

            is AppearanceScreenEvents.OnColorThemeDialogShowRequest -> {
                onColorThemeDialogShowRequest()
            }
        }
    }
}