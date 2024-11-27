package dev.than0s.aluminium.features.post.presentation.screens.post_upload

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.SnackbarController
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.use_cases.AddLikeUseCase
import dev.than0s.aluminium.features.post.domain.use_cases.AddPostUseCase
import dev.than0s.aluminium.features.post.domain.use_cases.RemoveLikeUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostUploadViewModel @Inject constructor(
    private val addPostUserCase: AddPostUseCase,
) : ViewModel() {
    var post by mutableStateOf(Post())

    fun onTitleChange(title: String) {
        post = post.copy(title = title)
    }

    fun onDescriptionChange(description: String) {
        post = post.copy(description = description)
    }

    fun onFileUriChange(uri: Uri) {
        post = post.copy(file = uri)
    }

    fun onUploadClick(onSuccess: () -> Unit) {
        viewModelScope.launch {
            when (val result = addPostUserCase.invoke(post)) {
                is Either.Left -> {
                    SnackbarController.showSnackbar(result.value.message)
                }

                is Either.Right -> {
                    SnackbarController.showSnackbar("Post added successfully")
                    onSuccess()
                }
            }
        }
    }
}