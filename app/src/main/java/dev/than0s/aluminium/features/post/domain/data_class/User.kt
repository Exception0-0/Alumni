package dev.than0s.aluminium.features.post.domain.data_class

import android.net.Uri

data class User(
    val userId: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val profileImage: Uri = Uri.EMPTY
)
