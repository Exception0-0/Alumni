package dev.than0s.aluminium.features.chat.presentation.screens.group_list

sealed class EventsGroupList {
    data object LoadGroup : EventsGroupList()
    data object OnNewMessageClick : EventsGroupList()
    data class GetChatMessage(
        val receiverId: String,
        val messageId: String,
    ) : EventsGroupList()

    data class GetLastSeen(val userId: String) : EventsGroupList()
}