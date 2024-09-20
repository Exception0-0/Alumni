package dev.than0s.aluminium.features.post.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.post.domain.data_class.PostFile
import dev.than0s.aluminium.features.post.domain.repository.PostRepository
import javax.inject.Inject

class AddPostFileUseCase @Inject constructor(private val repository: PostRepository) :
    UseCase<PostFile, Unit> {
    override suspend fun invoke(param: PostFile): Either<Failure, Unit> {
        return repository.addPostFile(
            uri = param.uri,
            id = param.id
        )
    }
}