package dev.than0s.aluminium.features.notification.presentation.push_notification

sealed class EventsPushNotification {
    data class ChangeTitle(val title: String) : EventsPushNotification()
    data class ChangeContent(val content: String) : EventsPushNotification()
    data object PushNotification : EventsPushNotification()
}