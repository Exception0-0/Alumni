package dev.than0s.aluminium.features.profile.presentation.screens.profile

import android.net.Uri

sealed class ProfileEvents {
    data class OnFirstNameChanged(val name: String) : ProfileEvents()
    data class OnLastNameChanged(val name: String) : ProfileEvents()
    data class OnBioChanged(val name: String) : ProfileEvents()
    data class OnProfileImageChanged(val uri: Uri?) : ProfileEvents()
    data class OnCoverImageChanged(val uri: Uri?) : ProfileEvents()
    data class OnTabChanged(val tabIndex: Int) : ProfileEvents()
    data object OnProfileUpdateClick : ProfileEvents()
    data object OnLoadProfile : ProfileEvents()
    data object OnEditProfileClick : ProfileEvents()
    data object OnUpdateDialogDismissRequest : ProfileEvents()
}