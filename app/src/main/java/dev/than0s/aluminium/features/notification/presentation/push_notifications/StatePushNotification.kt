package dev.than0s.aluminium.features.notification.presentation.push_notifications

import dev.than0s.aluminium.features.notification.domain.data_class.AlumniFilter
import dev.than0s.aluminium.features.notification.domain.data_class.BatchFilter
import dev.than0s.aluminium.features.notification.domain.data_class.NotificationContent
import dev.than0s.aluminium.features.notification.domain.data_class.StudentFilter

data class StatePushNotification(
    val content: NotificationContent = NotificationContent(),
    val studentFilter: StudentFilter = StudentFilter(),
    val alumniFilter: AlumniFilter = AlumniFilter(),
    val studentBatch: BatchFilter = BatchFilter(),
    val alumniBatch: BatchFilter = BatchFilter(),
    val isStudentBatch: Boolean = false,
    val isAlumniBatch: Boolean = false,
    val student: Boolean = true,
    val alumni: Boolean = true,
    val staff: Boolean = true,
)