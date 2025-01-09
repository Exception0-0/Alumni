package dev.than0s.aluminium.features.chat.data.mapper

import com.google.firebase.firestore.DocumentId
import dev.than0s.aluminium.features.chat.domain.data_class.ChatGroup

fun ChatGroup.toRemoteChatGroup() = RemoteChatGroup(
    id = id,
    usersId = usersId
)

fun RemoteChatGroup.toChatGroup() = ChatGroup(
    id = id,
    usersId = usersId
)

data class RemoteChatGroup(
    @DocumentId
    val id: String = "",
    val usersId: List<String> = emptyList()
)