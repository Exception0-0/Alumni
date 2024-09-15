package dev.than0s.aluminium.features.post.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.repository.PostRepository
import javax.inject.Inject

class SetPostUseCase @Inject constructor(private val repository: PostRepository) :
    UseCase<Post, Unit> {
    override suspend fun invoke(param: Post): Either<Failure, Unit> {
        val fileId = System.currentTimeMillis().toString()
        return when (val fileResult = repository.setPostFile(param.file, fileId)) {
            is Either.Right -> {
                when (val docResult = repository.setPost(param.copy(id = fileId))) {
                    is Either.Right -> {
                        Either.Right(docResult.value)
                    }

                    is Either.Left -> Either.Left(docResult.value)
                }
            }

            is Either.Left -> Either.Left(fileResult.value)
        }
    }
}