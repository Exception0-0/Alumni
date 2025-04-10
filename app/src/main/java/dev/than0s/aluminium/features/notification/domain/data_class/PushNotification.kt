package dev.than0s.aluminium.features.notification.domain.data_class

import android.net.Uri
import com.google.firebase.firestore.DocumentId

data class PushNotification(
    @DocumentId
    val id: String = "",
    val content: NotificationContent = NotificationContent(),
    val filters: Filters = Filters(),
    val pushStatus: Boolean = false,
    val timestamp: Long = 0L
)

data class NotificationContent(
    val title: String = "",
    val content: String = "",
    val icon: Uri? = null,
)

data class Filters(
    val student: StudentFilter? = StudentFilter(),
    val alumni: AlumniFilter? = AlumniFilter(),
    val staff: Boolean = true,
)

data class StudentFilter(
    val mca: Boolean = true,
    val mba: Boolean = true,
    val batch: BatchFilter? = BatchFilter(),
)

data class AlumniFilter(
    val mca: Boolean = true,
    val mba: Boolean = true,
    val batch: BatchFilter? = BatchFilter(),
)

data class BatchFilter(
    val from: String = "",
    val to: String = "",
)
