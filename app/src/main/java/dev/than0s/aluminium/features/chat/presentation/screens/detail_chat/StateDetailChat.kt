package dev.than0s.aluminium.features.chat.presentation.screens.detail_chat

import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.domain.error.PreferredError
import dev.than0s.aluminium.features.chat.domain.data_class.ChatMessage
import dev.than0s.aluminium.features.last_seen.domain.data_class.UserStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class StateDetailChat(
    val isLoading: Boolean = false,
    val isSending: Boolean = false,
    val isDeleting: Boolean = false,
    val topDropDownMenu: Boolean = false,
    val deleteDialog: String? = null,
    val clearAllChatDialog: Boolean = false,
    val userStatus: Flow<UserStatus> = emptyFlow(),
    val chatMessage: String = "",
    val otherUser: User = User(),
    val chatFlow: Flow<List<ChatMessage>> = emptyFlow(),
    val messageError: PreferredError? = null
)
