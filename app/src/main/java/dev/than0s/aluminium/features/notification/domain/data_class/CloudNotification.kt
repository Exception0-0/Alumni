package dev.than0s.aluminium.features.notification.domain.data_class

import android.net.Uri
import com.google.firebase.firestore.DocumentId

data class CloudNotification(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val icon: Uri? = null,
    val timestamp: Long = 0L,
)
