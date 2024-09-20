package dev.than0s.aluminium.features.post.domain.use_cases

import android.net.Uri
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.data_class.PostFile
import dev.than0s.aluminium.features.post.domain.repository.PostRepository
import javax.inject.Inject

class SetPostFileUseCase @Inject constructor(private val repository: PostRepository) :
    UseCase<PostFile, Unit> {
    override suspend fun invoke(param: PostFile): Either<Failure, Unit> {
        return repository.setPostFile(
            uri = param.uri,
            id = param.id
        )
    }
}