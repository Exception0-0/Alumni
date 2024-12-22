package dev.than0s.aluminium.features.profile.presentation.screens.settings

import dev.than0s.aluminium.core.domain.data_class.User

data class SettingsState(
    val user: User = User(),
    val isLoading: Boolean = false,
)