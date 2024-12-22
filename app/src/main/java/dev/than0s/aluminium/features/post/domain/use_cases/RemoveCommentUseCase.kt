package dev.than0s.aluminium.features.post.domain.use_cases

import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.features.post.domain.data_class.Comment
import dev.than0s.aluminium.features.post.domain.repository.CommentRepository
import javax.inject.Inject

class RemoveCommentUseCase @Inject constructor(private val repository: CommentRepository) {
    suspend operator fun invoke(comment: Comment): SimpleResource {
        return repository.removeComment(comment)
    }
}