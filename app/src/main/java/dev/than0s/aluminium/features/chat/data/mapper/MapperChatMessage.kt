package dev.than0s.aluminium.features.chat.data.mapper

import dev.than0s.aluminium.features.chat.domain.data_class.ChatMessage

fun ChatMessage.toRemoteChatMessage() = RemoteChatMessage(
    message = message,
    userId = userId,
    timestamp = timestamp
)

fun RemoteChatMessage.toChatMessage(id: String) = ChatMessage(
    id = id,
    message = message,
    userId = userId,
    timestamp = timestamp
)

data class RemoteChatMessage(
    val message: String = "",
    val userId: String = "",
    val timestamp: Long = 0,
)