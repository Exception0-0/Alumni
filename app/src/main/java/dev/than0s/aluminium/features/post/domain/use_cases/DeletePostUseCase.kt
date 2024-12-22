package dev.than0s.aluminium.features.post.domain.use_cases

import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.repository.PostRepository
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(private val repository: PostRepository) {
    suspend operator fun invoke(postId: String): SimpleResource {
        return repository.deletePost(postId)
    }
}