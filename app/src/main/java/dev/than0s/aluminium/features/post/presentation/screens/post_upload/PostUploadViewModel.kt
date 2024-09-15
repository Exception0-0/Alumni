package dev.than0s.aluminium.features.post.presentation.screens.post_upload

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.use_cases.SetPostUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostUploadViewModel @Inject constructor(
    private val setPostUseCase: SetPostUseCase,
) : ViewModel() {
    var post by mutableStateOf(Post())
    var circularProgressIndicatorState by mutableStateOf(false)

    fun onTitleChange(title: String) {
        post = post.copy(title = title)
    }

    fun onDescriptionChange(description: String) {
        post = post.copy(description = description)
    }

    fun onFileUriChange(uri: Uri) {
        post = post.copy(file = uri)
    }

    fun onUploadClick(popScreen: () -> Unit) {
        viewModelScope.launch {
            when (val fileResult = setPostUseCase.invoke(post)) {
                is Either.Left -> TODO("show error message")
                is Either.Right -> {
                    popScreen()
                    TODO("show success message")
                }
            }
        }
    }
}