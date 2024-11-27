package dev.than0s.aluminium.features.post.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.post.domain.repository.PostRepository
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(private val repository: PostRepository) :
    UseCase<String, Unit> {
    override suspend fun invoke(id: String): Either<Failure, Unit> {
        return repository.deletePost(id)
    }
}