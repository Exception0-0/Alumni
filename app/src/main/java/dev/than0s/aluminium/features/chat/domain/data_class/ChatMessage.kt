package dev.than0s.aluminium.features.chat.domain.data_class

data class ChatMessage(
    val id: String = "",
    val userId: String = "",
    val message: String = "",
    val timestamp: Long = 0,
)
