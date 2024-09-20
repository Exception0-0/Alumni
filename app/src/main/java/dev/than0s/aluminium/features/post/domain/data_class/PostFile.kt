package dev.than0s.aluminium.features.post.domain.data_class

import android.net.Uri

data class PostFile(
    val uri: Uri = Uri.EMPTY,
    val id: String = "",
)