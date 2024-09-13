package dev.than0s.aluminium.core.data_class

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Post(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val fileId: String? = null,
    val timestamp: Timestamp = Timestamp.now(),
)