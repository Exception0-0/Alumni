package dev.than0s.aluminium.features.post.domain.data_class

import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentId

data class Comment(
    @DocumentId
    val id: String = System.currentTimeMillis().toString(),
    val postId: String = "",
    val userId: String = Firebase.auth.currentUser!!.uid,
    val message: String = "",
    val timeStamp: Timestamp = Timestamp.now()
)
