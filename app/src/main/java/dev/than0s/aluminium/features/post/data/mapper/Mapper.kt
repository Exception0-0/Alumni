package dev.than0s.aluminium.features.post.data.mapper

import android.net.Uri
import com.google.firebase.Timestamp
import dev.than0s.aluminium.features.post.domain.data_class.Comment
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.data_class.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

suspend fun List<RawPost>.toPost(
    getFile: suspend (String) -> Uri,
): List<Post> {
    return this.map {
        Post(
            id = it.id,
            userId = it.userId,
            file = getFile(it.id),
            title = it.title,
            description = it.description,
            timestamp = it.timestamp,
        )
    }
}

fun Post.toRawPost() = RawPost(
    id = id,
    userId = userId,
    title = title,
    description = description,
    timestamp = timestamp
)

data class RawPost(
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val timestamp: Timestamp = Timestamp.now()
)