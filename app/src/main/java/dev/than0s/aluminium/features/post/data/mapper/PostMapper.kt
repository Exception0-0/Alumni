package dev.than0s.aluminium.features.post.data.mapper

import android.net.Uri
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import dev.than0s.aluminium.core.data.remote.getFirebaseTimestamp
import dev.than0s.aluminium.core.domain.data_class.Post

fun RemotePost.toPost(): Post = Post(
    id = id,
    userId = userId,
    file = Uri.parse(file),
    title = title,
    description = description,
    timestamp = timestamp.seconds,
)

fun Post.toRemotePost() = RemotePost(
    id = id,
    userId = userId,
    file = file.toString(),
    title = title,
    description = description,
    timestamp = getFirebaseTimestamp(timestamp)
)

data class RemotePost(
    @DocumentId
    val id: String = "",
    val userId: String = "",
    val file: String = "",
    val title: String = "",
    val description: String = "",
    val timestamp: Timestamp = Timestamp.now()
)