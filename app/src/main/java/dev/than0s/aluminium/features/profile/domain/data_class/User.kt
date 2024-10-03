package dev.than0s.aluminium.features.profile.domain.data_class

import android.media.Image
import android.net.Uri

data class User(
    val profileImage: Uri? = null,
    val coverImage: Uri? = null,
    val firstName: String = "",
    val lastName: String = "",
    val bio: String = "",
)