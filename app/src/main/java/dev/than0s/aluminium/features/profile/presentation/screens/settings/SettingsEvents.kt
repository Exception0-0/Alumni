package dev.than0s.aluminium.features.profile.presentation.screens.settings

sealed class SettingsEvents {
    data class OnSignOut(val restartApp: () -> Unit) : SettingsEvents()
    data object ShowLogoutDialog : SettingsEvents()
    data object DismissLogoutDialog : SettingsEvents()
}