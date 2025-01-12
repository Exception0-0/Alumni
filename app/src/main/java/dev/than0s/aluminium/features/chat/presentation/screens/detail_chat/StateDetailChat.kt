package dev.than0s.aluminium.features.chat.presentation.screens.detail_chat

import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.domain.error.Error
import dev.than0s.aluminium.features.chat.domain.data_class.ChatMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class StateDetailChat(
    val isLoading: Boolean = false,
    val isSending: Boolean = false,
    val chatMessage: String = "",
    val otherUser: User = User(),
    val chatFlow: Flow<List<ChatMessage>> = emptyFlow(),
    val messageError: Error? = null
)
