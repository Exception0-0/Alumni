package dev.than0s.aluminium.features.chat.domain.data_class

data class ChatGroup(
    val receiverId: String = "",
    val message: ChatMessage = ChatMessage(),
)
