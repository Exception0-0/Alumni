package dev.than0s.aluminium.features.post.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.post.domain.data_class.Comment
import dev.than0s.aluminium.features.post.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCommentFlowUseCase @Inject constructor(private val repository: PostRepository) :
    UseCase<String, Flow<List<Comment>>> {
    override suspend fun invoke(postId: String): Either<Failure, Flow<List<Comment>>> {
        return repository.getCommentFlow(postId)
    }
}