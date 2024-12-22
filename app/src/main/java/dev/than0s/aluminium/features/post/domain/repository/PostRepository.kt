package dev.than0s.aluminium.features.post.domain.repository

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.domain.data_class.Post

interface PostRepository {
    suspend fun addPost(post: Post): SimpleResource
    suspend fun deletePost(postId: String): SimpleResource
    suspend fun getPosts(userId: String?): Resource<List<Post>>
}