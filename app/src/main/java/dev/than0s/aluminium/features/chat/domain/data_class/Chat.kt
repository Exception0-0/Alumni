package dev.than0s.aluminium.features.chat.domain.data_class

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Chat(
    @DocumentId
    val id: String = System.currentTimeMillis().toString(),
    val message: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val timeStamp: Timestamp = Timestamp.now()
)
