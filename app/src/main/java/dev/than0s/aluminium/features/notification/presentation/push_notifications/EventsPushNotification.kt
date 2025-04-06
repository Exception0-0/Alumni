package dev.than0s.aluminium.features.notification.presentation.push_notifications

sealed class EventsPushNotification {
    data class ChangeTitle(val title: String) : EventsPushNotification()
    data class ChangeContent(val content: String) : EventsPushNotification()
    data object PushNotification : EventsPushNotification()
    data object ChangeStudentFilter : EventsPushNotification()
    data object ChangeAlumniFilter : EventsPushNotification()
    data object ChangeStaffFilter : EventsPushNotification()
    data object ChangeStudentMcaFilter : EventsPushNotification()
    data object ChangeStudentMbaFilter : EventsPushNotification()
    data object ChangeAlumniMcaFilter : EventsPushNotification()
    data object ChangeAlumniMbaFilter : EventsPushNotification()
    data object ChangeStudentBatchFilter : EventsPushNotification()
    data object ChangeAlumniBatchFilter : EventsPushNotification()
    data class ChangeStudentBatchFrom(val year: String) : EventsPushNotification()
    data class ChangeStudentBatchTo(val year: String) : EventsPushNotification()
    data class ChangeAlumniBatchFrom(val year: String) : EventsPushNotification()
    data class ChangeAlumniBatchTo(val year: String) : EventsPushNotification()
}