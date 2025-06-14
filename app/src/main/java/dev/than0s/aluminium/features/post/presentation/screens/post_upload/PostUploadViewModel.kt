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
import dev.than0s.aluminium.core.presentation.utils.SnackbarAction
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
    var screenStatus by mutableStateOf(PostUploadScreenStatus())

    private fun onCaptionChanged(caption: String) {
        screenStatus = screenStatus.copy(
            post = screenStatus.post.copy(
                caption = caption
            )
        )
    }

    private fun onImagesSelected(images: List<Uri>) {
        screenStatus = screenStatus.copy(
            post = screenStatus.post.copy(
                files = images
            )
        )
    }

    private fun onUploadClick(
        popScreen: () -> Unit,
    ) {
        viewModelScope.launch {
            screenStatus = screenStatus.copy(isLoading = true)

            val addPostResult = addPostUserCase(screenStatus.post)

            addPostResult.let {
                screenStatus = screenStatus.copy(
                    descriptionError = it.descriptionError,
                    fileError = it.fileError
                )
            }

            when (addPostResult.result) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = addPostResult.result.uiText ?: UiText.unknownError(),
                            action = SnackbarAction(
                                name = UiText.StringResource(R.string.try_again),
                                action = {
                                    onUploadClick(popScreen = popScreen)
                                }
                            )
                        )
                    )
                }

                is Resource.Success -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = UiText.StringResource(R.string.successfully_post_uploaded)
                        )
                    )
                    popScreen()
                }

                null -> {}
            }

            screenStatus = screenStatus.copy(isLoading = false)
        }
    }

    fun onEvent(event: PostUploadScreenEvents) {
        when (event) {
            is PostUploadScreenEvents.OnCaptionChanged -> onCaptionChanged(caption = event.text)
            is PostUploadScreenEvents.OnImagesSelected -> onImagesSelected(images = event.images)
            is PostUploadScreenEvents.OnUploadClick -> onUploadClick(popScreen = event.popScreen)
        }
    }
}