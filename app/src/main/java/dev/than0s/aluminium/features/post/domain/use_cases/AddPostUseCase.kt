package dev.than0s.aluminium.features.post.domain.use_cases

import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.domain.data_class.Post
import dev.than0s.aluminium.core.domain.util.generateUniqueId
import dev.than0s.aluminium.core.presentation.error.TextFieldError
import dev.than0s.aluminium.features.post.domain.data_class.AddPostResult
import dev.than0s.aluminium.features.post.domain.repository.PostRepository
import javax.inject.Inject

class AddPostUseCase @Inject constructor(private val repository: PostRepository) {
    suspend operator fun invoke(post: Post): AddPostResult {
        val descriptionError = post.caption.let {
            if (it.isBlank()) TextFieldError.FieldEmpty
            else null
        }
        val fileError = post.files.let {
            if (it.isEmpty()) TextFieldError.FieldEmpty
            else null
        }

        if (descriptionError != null || fileError != null) {
            return AddPostResult(
                descriptionError = descriptionError,
                fileError = fileError
            )
        }

        return AddPostResult(
            result = repository.addPost(
                post.copy(
                    id = generateUniqueId(),
                    userId = currentUserId!!,
                    timestamp = System.currentTimeMillis()
                )
            )
        )
    }
}