package dev.than0s.aluminium.features.chat.domain.data_class

import android.net.Uri

data class ChatListItem(
    val userId: String = "",
    val userFirstName: String = "",
    val userLastName: String = "",
    val userProfileImage: Uri = Uri.EMPTY,
    val lastChat:Chat = Chat()
)
