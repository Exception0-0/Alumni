package dev.than0s.aluminium.features.profile.presentation.dialogs.update_profile

import android.net.Uri

sealed class UpdateProfileDialogEvents {
    data class OnFirstNameChanged(val name: String) : UpdateProfileDialogEvents()
    data class OnLastNameChanged(val name: String) : UpdateProfileDialogEvents()
    data class OnBioChanged(val name: String) : UpdateProfileDialogEvents()
    data class OnProfileImageChanged(val uri: Uri) : UpdateProfileDialogEvents()
    data class OnCoverImageChanged(val uri: Uri) : UpdateProfileDialogEvents()
    data class OnProfileUpdateClick(val onSuccess: () -> Unit) : UpdateProfileDialogEvents()
    data class OnSignOutClick(val restartApp: () -> Unit) : UpdateProfileDialogEvents()
}