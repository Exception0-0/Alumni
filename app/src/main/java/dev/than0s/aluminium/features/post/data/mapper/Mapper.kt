package dev.than0s.aluminium.features.post.data.mapper

import android.net.Uri
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.data_class.RawPost
import dev.than0s.aluminium.features.post.domain.data_class.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

suspend fun Flow<List<RawPost>>.toPost(
    getUser: suspend (String) -> User,
    getFile: suspend (String) -> Uri,
): Flow<List<Post>> {
    return this.map { list ->
        list.map {
            Post(
                id = it.id,
                user = getUser(it.userId),
                file = getFile(it.id),
                title = it.title,
                description = it.description,
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