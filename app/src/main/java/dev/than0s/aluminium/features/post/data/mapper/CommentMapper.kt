package dev.than0s.aluminium.features.post.data.mapper

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import dev.than0s.aluminium.core.domain.data_class.Comment

data class RemoteComment(
    @DocumentId
    val id: String = "",
    val userId: String = "",
    val message: String = "",
    val timestamp: Timestamp = Timestamp.now()
)

fun Comment.toRemoteComment() = RemoteComment(
    id = id,
    userId = userId,
    message = message,
    timestamp = Timestamp(timestamp, 0)
)

fun RemoteComment.toComment(postId: String) = Comment(
    id = id,
    postId = postId,
    userId = userId,
    message = message,
    timestamp = timestamp.seconds
)