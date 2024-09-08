package dev.than0s.aluminium.core.data_class

import android.net.Uri

data class User(
    val id: String = "",
    val profileImage: Uri = Uri.EMPTY,
    val firstName: String = "",
    val lastName: String = "",
    val bio: String = "",
)