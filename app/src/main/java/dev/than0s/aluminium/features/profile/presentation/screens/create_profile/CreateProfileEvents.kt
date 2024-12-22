package dev.than0s.aluminium.features.profile.presentation.screens.create_profile

import android.net.Uri

sealed class CreateProfileEvents {
    data class OnFirstNameChanged(val name: String) : CreateProfileEvents()
    data class OnLastNameChanged(val name: String) : CreateProfileEvents()
    data class OnBioChanged(val name: String) : CreateProfileEvents()
    data class OnProfileImageChanged(val uri: Uri?) : CreateProfileEvents()
    data class OnCoverImageChanged(val uri: Uri?) : CreateProfileEvents()
    data class OnProfileSaveClick(val restartApp: () -> Unit) : CreateProfileEvents()
}