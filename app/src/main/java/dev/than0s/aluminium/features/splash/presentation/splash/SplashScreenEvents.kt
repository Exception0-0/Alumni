package dev.than0s.aluminium.features.splash.presentation.splash

import dev.than0s.aluminium.core.Screen

sealed class SplashScreenEvents {
    data class OnLoad(val popAndOpen: (Screen) -> Unit) : SplashScreenEvents()
}