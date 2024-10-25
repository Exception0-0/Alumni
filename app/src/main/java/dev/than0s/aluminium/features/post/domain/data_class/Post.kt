package dev.than0s.aluminium.features.post.domain.data_class

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentId

data class Post(
    @DocumentId
    val id: String = "",
    val userId: String = "",
    val file: Uri = Uri.EMPTY,
    val title: String = "",
    val description: String = "",
    val isLiked: Boolean = false,
    val timestamp: Timestamp = Timestamp.now(),
)