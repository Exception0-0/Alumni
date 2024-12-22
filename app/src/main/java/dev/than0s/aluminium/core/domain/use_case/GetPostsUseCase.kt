package dev.than0s.aluminium.core.domain.use_case

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.repository.PostRepository
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val repository: PostRepository) {
    suspend operator fun invoke(userId: String? = null): Resource<List<Post>> {
        return repository.getPosts(userId)
    }
}