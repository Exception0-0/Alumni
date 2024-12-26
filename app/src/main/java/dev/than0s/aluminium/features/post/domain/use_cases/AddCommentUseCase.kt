package dev.than0s.aluminium.features.post.domain.use_cases

import com.google.firebase.Timestamp
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.domain.util.generateUniqueId
import dev.than0s.aluminium.core.presentation.error.TextFieldError
import dev.than0s.aluminium.features.post.domain.data_class.AddCommentResult
import dev.than0s.aluminium.core.domain.data_class.Comment
import dev.than0s.aluminium.features.post.domain.repository.CommentRepository
import javax.inject.Inject

class AddCommentUseCase @Inject constructor(private val repository: CommentRepository) {
    suspend operator fun invoke(comment: Comment): AddCommentResult {
        val commentError = comment.let {
            if (it.message.isBlank()) TextFieldError.FieldEmpty
            else null
        }
        if (commentError != null) {
            return AddCommentResult(
                messageError = commentError
            )
        }
        return AddCommentResult(
            result = repository.addComment(
                comment.copy(
                    id = generateUniqueId(),
                    userId = currentUserId!!,
                    timestamp = System.currentTimeMillis()
                )
            )
        )
    }
}