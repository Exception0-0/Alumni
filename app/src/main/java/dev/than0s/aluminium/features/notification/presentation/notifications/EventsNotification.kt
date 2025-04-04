package dev.than0s.aluminium.features.notification.presentation.notifications

import dev.than0s.aluminium.features.notification.domain.data_class.CloudNotification

sealed class EventsNotification {
    data object GetNotifications : EventsNotification()
    data class RemoteNotification(val notification: CloudNotification) : EventsNotification()
}