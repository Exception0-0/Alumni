package dev.than0s.aluminium.features.post.data.mapper

import android.net.Uri
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import dev.than0s.aluminium.core.data.remote.getFirebaseTimestamp
import dev.than0s.aluminium.core.domain.data_class.Post

fun RemotePost.toPost(): Post = Post(
    id = id,
    userId = userId,
    files = files.map { Uri.parse(it) },
    caption = caption,
    timestamp = timestamp.toDate().time,
)

fun Post.toRemotePost() = RemotePost(
    id = id,
    userId = userId,
    files = files.map { it.toString() },
    caption = caption,
    timestamp = getFirebaseTimestamp(timestamp)
)

data class RemotePost(
    @DocumentId
    val id: String = "",
    val userId: String = "",
    val files: List<String> = emptyList(),
    val caption: String = "",
    val timestamp: Timestamp = Timestamp.now()
)