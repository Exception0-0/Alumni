package dev.than0s.aluminium.core.domain.data_class

import android.net.Uri

data class Post(
    val id: String = "",
    val userId: String = "",
    val file: Uri? = Uri.EMPTY,
    val title: String = "",
    val description: String = "",
    val timestamp: Long = 0,
)