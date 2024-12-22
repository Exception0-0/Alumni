package dev.than0s.aluminium.features.post.domain.use_cases

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.features.post.domain.data_class.Comment
import dev.than0s.aluminium.features.post.domain.repository.CommentRepository
import javax.inject.Inject

class GetCommentUseCase @Inject constructor(private val repository: CommentRepository) {
    suspend operator fun invoke(postId: String): Resource<List<Comment>> {
        return repository.getComments(postId)
    }
}