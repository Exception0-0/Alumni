package dev.than0s.aluminium.features.post.data.data_source

import com.google.firebase.Timestamp
import dev.than0s.aluminium.core.domain.data_class.Like

fun Like.toRemoteLike() = RemoteLike(
    userId = userId,
    timestamp = timestamp
)

fun RemoteLike.toLike(postId: String) = Like(
    userId = userId,
    postId = postId,
    timestamp = timestamp
)

data class RemoteLike(
    val id: String = "",
    val userId: String = "",
    val timestamp: Timestamp
)