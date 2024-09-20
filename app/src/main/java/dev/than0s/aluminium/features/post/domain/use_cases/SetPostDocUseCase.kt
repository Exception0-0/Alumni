package dev.than0s.aluminium.features.post.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.repository.PostRepository
import javax.inject.Inject

class SetPostDocUseCase @Inject constructor(private val repository: PostRepository) :
    UseCase<Post, Unit> {
    override suspend fun invoke(param: Post): Either<Failure, Unit> {
        return repository.setPost(param)
    }
}