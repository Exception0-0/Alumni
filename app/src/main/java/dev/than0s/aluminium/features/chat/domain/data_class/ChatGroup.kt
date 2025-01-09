package dev.than0s.aluminium.features.chat.domain.data_class

data class ChatGroup(
    val id: String = "",
    val usersId: List<String> = emptyList()
)
