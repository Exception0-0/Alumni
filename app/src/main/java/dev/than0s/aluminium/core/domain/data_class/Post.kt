package dev.than0s.aluminium.core.domain.data_class

import android.net.Uri

data class Post(
    val id: String = "",
    val userId: String = "",
    val files: List<Uri> = emptyList(),
    val caption: String = "",
    val timestamp: Long = 0,
)