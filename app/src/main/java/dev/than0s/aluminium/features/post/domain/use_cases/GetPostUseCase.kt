package dev.than0s.aluminium.features.post.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.repository.PostRepository
import javax.inject.Inject

class GetPostUseCase @Inject constructor(private val repository: PostRepository) :
    UseCase<String, Post> {
    override suspend fun invoke(id: String): Either<Failure, Post> {
        return when (val fileResult = repository.getPostFile(id)) {
            is Either.Right -> {
                when (val docResult = repository.getPost(id)) {
                    is Either.Right -> {
                        Either.Right(docResult.value.copy(file = fileResult.value))
                    }

                    is Either.Left -> Either.Left(docResult.value)
                }
            }

            is Either.Left -> Either.Left(fileResult.value)
        }
    }
}