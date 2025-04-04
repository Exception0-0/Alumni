package dev.than0s.aluminium.features.notification.domain.data_class

import com.google.firebase.firestore.DocumentId

data class CloudMessagingToken(
    @DocumentId
    val userId: String = "",
    val token: String = "",
)
