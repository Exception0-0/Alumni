package dev.than0s.aluminium.core.domain.data_class

import android.net.Uri

data class User(
    val id: String = "",
    val profileImage: Uri? = null,
    val coverImage: Uri? = null,
    val firstName: String = "",
    val lastName: String = "",
    val bio: String = "",
)