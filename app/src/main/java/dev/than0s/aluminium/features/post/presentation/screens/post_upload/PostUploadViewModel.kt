package dev.than0s.aluminium.features.post.presentation.screens.post_upload

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.presentation.utils.SnackbarController
import dev.than0s.aluminium.core.presentation.utils.SnackbarEvent
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.features.post.domain.use_cases.AddPostUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostUploadViewModel @Inject constructor(
    private val addPostUserCase: AddPostUseCase,
) : ViewModel() {
    var screenStatus by mutableStateOf(PostStatus())

    private fun onTitleChanged(title: String) {
        screenStatus = screenStatus.copy(
            post = screenStatus.post.copy(
                title = title
            )
        )
    }

    private fun onDescriptionChanged(description: String) {
        screenStatus = screenStatus.copy(
            post = screenStatus.post.copy(
                description = description
            )
        )
    }

    private fun onFileUriChanged(uri: Uri) {
        screenStatus = screenStatus.copy(
            post = screenStatus.post.copy(
                file = uri
            )
        )
    }

    private fun onUploadClick() {
        viewModelScope.launch {
            screenStatus = screenStatus.copy(isLoading = true)

            val addPostResult = addPostUserCase(screenStatus.post)

            addPostResult.titleError?.let {
                screenStatus = screenStatus.copy(
                    titleError = it
                )
            }
            addPostResult.descriptionError?.let {
                screenStatus = screenStatus.copy(
                    descriptionError = it
                )
            }
            addPostResult.fileError?.let {
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = it.message ?: UiText.unknownError()
                    )
                )
            }

            when (addPostResult.result) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = addPostResult.result.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Success -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = UiText.StringResource(R.string.successfully_post_uploaded)
                        )
                    )
                }

                null -> {}
            }

            screenStatus = screenStatus.copy(isLoading = false)
        }
    }

    fun onEvent(event: PostEvents) {
        when (event) {
            is PostEvents.OnTitleChanged -> onTitleChanged(event.text)
            is PostEvents.OnDescriptionChanged -> onDescriptionChanged(event.text)
            is PostEvents.OnFileUriChanged -> onFileUriChanged(event.uri)
            is PostEvents.OnUploadClick -> onUploadClick()
        }
    }
}