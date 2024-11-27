package dev.than0s.aluminium.features.post.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.post.domain.data_class.Comment
import dev.than0s.aluminium.features.post.domain.repository.CommentRepository
import dev.than0s.aluminium.features.post.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCommentFlowUseCase @Inject constructor(private val repository: CommentRepository) :
    UseCase<String, List<Comment>> {
    override suspend fun invoke(postId: String): Either<Failure, List<Comment>> {
        return repository.getComments(postId)
    }
}