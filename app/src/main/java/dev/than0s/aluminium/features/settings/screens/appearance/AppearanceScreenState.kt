package dev.than0s.aluminium.features.settings.screens.appearance

import dev.than0s.aluminium.core.presentation.ui.ColorTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class AppearanceScreenState(
    val currentColorTheme: Flow<ColorTheme> = emptyFlow(),
    val colorThemeDialog: Boolean = false,
)
