package dev.than0s.aluminium.features.splash.presentation.splash

import dev.than0s.aluminium.core.presentation.utils.Screen

sealed class SplashScreenEvents {
    data class OnLoad(val replaceScreen: (Screen) -> Unit) : SplashScreenEvents()
}