package dev.than0s.aluminium.features.post.data.mapper

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import dev.than0s.aluminium.core.data.remote.getFirebaseTimestamp
import dev.than0s.aluminium.core.domain.data_class.Like

fun Like.toRemoteLike() = RemoteLike(
    userId = userId,
    timestamp = getFirebaseTimestamp(timestamp)
)

fun RemoteLike.toLike(postId: String) = Like(
    userId = userId,
    postId = postId,
    timestamp = timestamp.seconds
)

data class RemoteLike(
    @DocumentId
    val userId: String = "",
    val timestamp: Timestamp = Timestamp.now()
)