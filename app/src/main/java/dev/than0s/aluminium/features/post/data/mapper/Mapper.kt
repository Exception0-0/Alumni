package dev.than0s.aluminium.features.post.data.mapper

import android.net.Uri
import com.google.firebase.Timestamp
import dev.than0s.aluminium.core.domain.data_class.Post

fun List<RemotePost>.toPost(): List<Post> {
    return this.map {
        Post(
            id = it.id,
            userId = it.userId,
            file = Uri.parse(it.file),
            title = it.title,
            description = it.description,
            timestamp = it.timestamp,
        )
    }
}

fun Post.toRemotePost() = RemotePost(
    id = id,
    userId = userId,
    file = file.toString(),
    title = title,
    description = description,
    timestamp = timestamp
)

data class RemotePost(
    val id: String = "",
    val userId: String = "",
    val file: String = Uri.EMPTY.toString(),
    val title: String = "",
    val description: String = "",
    val timestamp: Timestamp = Timestamp.now()
)