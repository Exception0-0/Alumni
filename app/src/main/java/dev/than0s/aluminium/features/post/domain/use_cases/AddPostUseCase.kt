package dev.than0s.aluminium.features.post.domain.use_cases

import com.google.firebase.Timestamp
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.repository.PostRepository
import javax.inject.Inject

class AddPostUseCase @Inject constructor(private val repository: PostRepository) :
    UseCase<Post, Unit> {
    override suspend fun invoke(param: Post): Either<Failure, Unit> {
        val newPost = param.copy(
            id = System.currentTimeMillis().toString(),
            userId = currentUserId!!,
            timestamp = Timestamp.now()
        )
        return repository.addPost(newPost)
    }
}