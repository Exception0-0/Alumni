package dev.than0s.aluminium.core.presentation.ui

import androidx.compose.runtime.Composable
import dev.burnoo.compose.rememberpreference.rememberStringPreference


@Composable
fun getCurrentColorTheme(): ColorTheme {
    return rememberStringPreference(
        keyName = COLOR_THEME,
        initialValue = ColorTheme.System.name,
        defaultValue = ColorTheme.System.name
    ).value.let {
        ColorTheme.valueOf(it)
    }
}

enum class ColorTheme {
    System,
    Dark,
    Light
}

const val COLOR_THEME = "color_theme"
const val PURE_BLACK = "pure_black"
const val DYNAMIC_THEME = "dynamic_theme"
const val ROUNDED_CORNERS = "rounded_corners"