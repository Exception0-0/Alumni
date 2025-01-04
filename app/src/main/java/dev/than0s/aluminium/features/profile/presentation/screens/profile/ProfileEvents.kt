package dev.than0s.aluminium.features.profile.presentation.screens.profile

import android.net.Uri

sealed class ProfileEvents {
    data class OnTabChanged(val tabIndex: Int) : ProfileEvents()
    data class ShowFullScreenImage(val uri: Uri) : ProfileEvents()
    data object DismissFullScreenImage : ProfileEvents()
    data object OnLoadProfile : ProfileEvents()
}