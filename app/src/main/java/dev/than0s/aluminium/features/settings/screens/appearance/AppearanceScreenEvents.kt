package dev.than0s.aluminium.features.settings.screens.appearance

import android.content.Context
import dev.than0s.aluminium.core.presentation.ui.ColorTheme

sealed class AppearanceScreenEvents {
    data class OnColorThemeChanged(
        val context: Context,
        val theme: ColorTheme
    ) : AppearanceScreenEvents()

    data object OnColorThemeDialogDismissRequest : AppearanceScreenEvents()
    data object OnColorThemeDialogShowRequest : AppearanceScreenEvents()
}