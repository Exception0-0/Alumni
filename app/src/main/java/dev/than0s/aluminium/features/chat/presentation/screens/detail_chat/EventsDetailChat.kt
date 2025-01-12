package dev.than0s.aluminium.features.chat.presentation.screens.detail_chat

sealed class EventsDetailChat {
    data object LoadMessages : EventsDetailChat()
    data object AddMessage : EventsDetailChat()
    data class OnMessageChange(val message: String) : EventsDetailChat()
}