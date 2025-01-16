package dev.than0s.aluminium.features.chat.presentation.screens.group_list

import dev.than0s.aluminium.core.presentation.utils.Screen

sealed class EventsGroupList {
    data object LoadGroup : EventsGroupList()
    data object OnNewMessageClick : EventsGroupList()
    data class OpenChatDetailScreen(
        val receiverId: String,
        val openScreen: (Screen) -> Unit,
    ) : EventsGroupList()
}