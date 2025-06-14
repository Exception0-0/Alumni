package dev.than0s.aluminium.features.chat.presentation.screens.detail_chat

sealed class EventsDetailChat {
    data object LoadMessages : EventsDetailChat()
    data object AddMessage : EventsDetailChat()
    data object DismissDeleteDialog : EventsDetailChat()
    data object DeleteMessage : EventsDetailChat()
    data object ShowClearAllChatDialog : EventsDetailChat()
    data object ClearAllChat : EventsDetailChat()
    data object DismissClearAllChatDialog : EventsDetailChat()
    data object ChangeTopDropDownState : EventsDetailChat()
    data class OnMessageChange(val message: String) : EventsDetailChat()
    data class ShowDeleteDialog(val messageId: String) : EventsDetailChat()
}