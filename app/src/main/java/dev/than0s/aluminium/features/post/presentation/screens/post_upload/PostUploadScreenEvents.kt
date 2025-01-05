package dev.than0s.aluminium.features.post.presentation.screens.post_upload

import android.net.Uri

sealed class PostUploadScreenEvents {
    data class OnCaptionChanged(val text: String) : PostUploadScreenEvents()
    data class OnImagesSelected(val images: List<Uri>) : PostUploadScreenEvents()
    data class OnUploadClick(val popScreen: () -> Unit) : PostUploadScreenEvents()
}