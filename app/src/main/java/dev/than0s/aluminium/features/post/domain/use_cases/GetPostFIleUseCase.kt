package dev.than0s.aluminium.features.post.domain.use_cases

import android.net.Uri
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.repository.PostRepository
import java.net.URL
import javax.inject.Inject

class GetPostFIleUseCase @Inject constructor(private val repository: PostRepository) :
    UseCase<String, Uri> {
    override suspend fun invoke(id: String): Either<Failure, Uri> {
        return repository.getPostFile(id)
    }
}