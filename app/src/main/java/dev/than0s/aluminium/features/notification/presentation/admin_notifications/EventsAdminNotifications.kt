package dev.than0s.aluminium.features.notification.presentation.admin_notifications

sealed class EventsAdminNotifications {
    data object GetNotifications : EventsAdminNotifications()
}