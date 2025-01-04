package dev.than0s.aluminium.features.profile.presentation.screens.profile

import android.net.Uri
import dev.than0s.aluminium.core.domain.data_class.User

data class ProfileState(
    val user: User = User(),
    val fullScreenImage: Uri? = null,
    val isLoading: Boolean = false,
    val tabRowSelectedIndex: Int = 0,
)