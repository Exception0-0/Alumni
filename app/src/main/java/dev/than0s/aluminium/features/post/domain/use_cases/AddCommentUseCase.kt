package dev.than0s.aluminium.features.post.domain.use_cases

import com.google.firebase.Timestamp
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.post.domain.data_class.Comment
import dev.than0s.aluminium.features.post.domain.repository.CommentRepository
import dev.than0s.aluminium.features.post.domain.repository.PostRepository
import javax.inject.Inject

class AddCommentUseCase @Inject constructor(private val repository: CommentRepository) :
    UseCase<Comment, Unit> {
    override suspend fun invoke(param: Comment): Either<Failure, Unit> {
        val newComment = param.copy(
            id = System.currentTimeMillis().toString(),
            userId = currentUserId!!,
            timestamp = Timestamp.now()
        )
        return repository.addComment(newComment)
    }
}