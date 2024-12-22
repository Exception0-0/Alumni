package dev.than0s.aluminium.features.post.presentation.screens.post_upload

import android.net.Uri

sealed class PostEvents {
    data class OnTitleChanged(val text: String) : PostEvents()
    data class OnDescriptionChanged(val text: String) : PostEvents()
    data class OnFileUriChanged(val uri: Uri) : PostEvents()
    data object OnUploadClick : PostEvents()
}