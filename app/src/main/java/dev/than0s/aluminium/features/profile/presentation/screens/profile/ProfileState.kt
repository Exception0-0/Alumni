package dev.than0s.aluminium.features.profile.presentation.screens.profile

import dev.than0s.aluminium.core.domain.data_class.User

data class ProfileState(
    val user: User = User(),
    val isLoading: Boolean = false,
    val tabRowSelectedIndex: Int = 0,
)