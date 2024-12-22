package dev.than0s.aluminium.features.post.domain.use_cases

import android.net.Uri
import com.google.firebase.Timestamp
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.features.post.domain.data_class.AddPostResult
import dev.than0s.aluminium.core.domain.data_class.Post
import dev.than0s.aluminium.core.domain.util.uniqueIdGenerator
import dev.than0s.aluminium.core.presentation.TextFieldError
import dev.than0s.aluminium.features.post.domain.repository.PostRepository
import javax.inject.Inject

class AddPostUseCase @Inject constructor(private val repository: PostRepository) {
    suspend operator fun invoke(post: Post): AddPostResult {
        val titleError = post.title.let {
            if (it.isBlank()) TextFieldError.FieldEmpty
            else null
        }
        val descriptionError = post.description.let {
            if (it.isBlank()) TextFieldError.FieldEmpty
            else null
        }
        val fileError = post.file.let {
            if (it == Uri.EMPTY) TextFieldError.FieldEmpty
            else null
        }

        if (titleError != null || descriptionError != null || fileError != null) {
            return AddPostResult(
                titleError = titleError,
                descriptionError = descriptionError,
                fileError = fileError
            )
        }

        return AddPostResult(
            result = repository.addPost(
                post.copy(
                    id = uniqueIdGenerator(),
                    userId = currentUserId!!,
                    timestamp = Timestamp.now()
                )
            )
        )
    }
}