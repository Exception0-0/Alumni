package dev.than0s.aluminium.core.presentation.ui

import android.content.Context
import dev.than0s.aluminium.di.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//suspend fun setCurrentColorTheme(context: Context, theme: ColorTheme) {
//    context.dataStore.edit { settings ->
//        settings[COLOR_THEME] = theme.name
//    }
//}
//
//fun getCurrentColorTheme(context: Context): Flow<ColorTheme> {
//    return context.dataStore.data.map { preferences ->
//        preferences[COLOR_THEME]?.let {
//            ColorTheme.valueOf(it)
//        } ?: ColorTheme.System
//    }
//}

enum class ColorTheme {
    System,
    Dark,
    Light
}

const val COLOR_THEME = "color_theme"