package dev.than0s.aluminium.features.post.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllPostFlowUseCase @Inject constructor(private val repository: PostRepository) :
    UseCase<Unit, Flow<List<Post>>> {
    override suspend fun invoke(param: Unit): Either<Failure, Flow<List<Post>>> {
        return repository.getAllPostFlow()
    }
}