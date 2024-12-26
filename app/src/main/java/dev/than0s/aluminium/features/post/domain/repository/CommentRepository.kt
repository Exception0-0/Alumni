package dev.than0s.aluminium.features.post.domain.repository

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.domain.data_class.Comment

interface CommentRepository {
    suspend fun addComment(comment: Comment): SimpleResource
    suspend fun removeComment(comment: Comment): SimpleResource
    suspend fun getComments(postId: String): Resource<List<Comment>>
}