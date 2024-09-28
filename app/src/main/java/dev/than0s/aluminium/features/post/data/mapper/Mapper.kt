package dev.than0s.aluminium.features.post.data.mapper

import android.net.Uri
import com.google.firebase.Timestamp
import dev.than0s.aluminium.features.post.domain.data_class.Comment
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.data_class.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

suspend fun Flow<List<RawPost>>.toPost(
    getUser: suspend (String) -> User,
    getFile: suspend (String) -> Uri,
    hasUserLiked: suspend (String) -> Boolean
): Flow<List<Post>> {
    return this.map { list ->
        list.map {
            Post(
                id = it.id,
                user = getUser(it.userId),
                file = getFile(it.id),
                title = it.title,
                description = it.description,
                timestamp = it.timestamp,
                hasLiked = hasUserLiked(it.id),
            )
        }
    }
}

suspend fun Flow<List<RawComment>>.toComment(postId: String): Flow<List<Comment>> {
    return this.map { list ->
        list.map {
            Comment(
                id = it.id,
                postId = postId,
                userId = it.userId,
                message = it.message,
                timeStamp = it.timeStamp
            )
        }
    }
}

fun Post.toRawPost() = RawPost(
    id = id,
    userId = user.userId,
    title = title,
    description = description,
    timestamp = timestamp
)

fun Comment.toRawComment() = RawComment(
    id = id,
    userId = userId,
    message = message,
    timeStamp = timeStamp
)

data class RawPost(
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val timestamp: Timestamp = Timestamp.now()
)

data class RawComment(
    val id: String = "",
    val userId: String = "",
    val message: String = "",
    val timeStamp: Timestamp = Timestamp.now()
)