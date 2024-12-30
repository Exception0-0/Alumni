package dev.than0s.aluminium.features.settings.screens.appearance

sealed class AppearanceScreenEvents {
    data object OnColorThemeDialogDismissRequest : AppearanceScreenEvents()
    data object OnColorThemeDialogShowRequest : AppearanceScreenEvents()
}