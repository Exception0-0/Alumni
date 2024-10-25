package dev.than0s.aluminium.features.post.domain.data_class

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Comment(
    @DocumentId
    val id: String = "",
    val postId: String = "",
    val userId: String = "",
    val message: String = "",
    val timestamp: Timestamp = Timestamp.now()
)
