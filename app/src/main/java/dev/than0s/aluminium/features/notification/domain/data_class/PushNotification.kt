package dev.than0s.aluminium.features.notification.domain.data_class

import com.google.firebase.firestore.DocumentId

data class PushNotification(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val batch: Batch? = null,
    val categories: Categories = Categories(),
    val isCompleted: Boolean = false,
)

data class Batch(
    val from: String = "",
    val to: String = "",
)

data class Categories(
    val mca: Boolean = true,
    val mba: Boolean = true,
    val staff: Boolean = true,
    val alumni: Boolean = true,
)