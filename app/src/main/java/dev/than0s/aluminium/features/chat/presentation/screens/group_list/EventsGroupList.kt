package dev.than0s.aluminium.features.chat.presentation.screens.group_list

sealed class EventsGroupList {
    data object LoadGroup : EventsGroupList()
    data object OnNewMessageClick : EventsGroupList()
}