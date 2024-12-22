package dev.than0s.aluminium.core.domain.data_class

import com.google.firebase.Timestamp

data class Like(
    val userId: String = "",
    val postId: String = "",
    val timestamp: Timestamp = Timestamp.now()
)
