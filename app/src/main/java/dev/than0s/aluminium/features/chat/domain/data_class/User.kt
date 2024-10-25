package dev.than0s.aluminium.features.chat.domain.data_class

import android.net.Uri

data class User(
    val firstName: String = "",
    val lastName: String = "",
    val profileImage: Uri = Uri.EMPTY
)
