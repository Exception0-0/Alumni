package dev.than0s.aluminium.features.post.data.mapper

import android.net.Uri
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.data_class.RawPost
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Flow<List<RawPost>>.toPost(fileUri: Uri): Flow<List<Post>> {
    return this.map { list ->
        list.map {
            Post(
                id = it.id,
                userId = it.userId,
                file = fileUri,
                title = it.title,
                description = it.description,
            )
        }
    }
}

fun Post.toRawPost() = RawPost(
    id = id,
    userId = userId,
    title = title,
    description = description,
)