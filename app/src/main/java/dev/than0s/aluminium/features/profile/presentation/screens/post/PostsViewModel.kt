package dev.than0s.aluminium.features.profile.presentation.screens.post

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.presentation.utils.SnackbarController
import dev.than0s.aluminium.core.presentation.utils.SnackbarEvent
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.core.domain.data_class.Like
import dev.than0s.aluminium.core.domain.use_case.AddLikeUseCase
import dev.than0s.aluminium.core.domain.use_case.GetCurrentUserLikeStatusUseCase
import dev.than0s.aluminium.core.domain.use_case.GetPostsUseCase
import dev.than0s.aluminium.core.domain.use_case.RemoveLikeUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val removeLikeUseCase: RemoveLikeUseCase,
    private val addLikeUseCase: AddLikeUseCase,
    private val getCurrentUserLikeStatusUseCase: GetCurrentUserLikeStatusUseCase,
) : ViewModel() {
    var userId = ""
    var screenState by mutableStateOf(PostsState())
    val likeMap = mutableStateMapOf<String, Like?>()

    private fun getUserPosts() {
        viewModelScope.launch {
            screenState = screenState.copy(isLoading = true)

            when (val result = getPostsUseCase(userId)) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Success -> {
                    screenState = screenState.copy(
                        postList = result.data!!
                    )
                }
            }

            screenState = screenState.copy(isLoading = false)
        }
    }

    private fun removeLike(postId: String) {
        viewModelScope.launch {
            when (val result = removeLikeUseCase(likeMap[postId]!!)) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Success -> {
                    likeMap.remove(postId)
                }
            }
        }
    }

    private fun addLike(postId: String) {
        viewModelScope.launch {
            val like = Like(postId = postId);
            when (val result = addLikeUseCase(like)) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Success -> {
                    likeMap.getLike(postId)
                }
            }
        }
    }

    private fun SnapshotStateMap<String, Like?>.getLike(postId: String) {
        if (!containsKey(postId)) {
            viewModelScope.launch {
                when (val result = getCurrentUserLikeStatusUseCase(postId)) {
                    is Resource.Error -> {
                        // TODO: do something on error
                    }

                    is Resource.Success -> {
                        this@getLike[postId] = result.data
                    }
                }
            }
        }
    }

    private fun onPostClick(postId: String) {
        screenState = screenState.copy(postDialog = postId)
    }

    private fun onPostDialogDismissRequest() {
        screenState = screenState.copy(postDialog = null)
    }

    fun onEvent(event: PostsEvents) {
        when (event) {
            is PostsEvents.OnPostClick -> {
                onPostClick(event.postId)
            }

            is PostsEvents.OnPostDialogDismissRequest -> {
                onPostDialogDismissRequest()
            }

            is PostsEvents.LoadPosts -> {
                getUserPosts()
            }

            is PostsEvents.OnLikeClick -> {
                if (event.hasLike) {
                    removeLike(event.postId)
                } else {
                    addLike(event.postId)
                }
            }

            is PostsEvents.GetLike ->{
                likeMap.getLike(event.postId)
            }
        }
    }
}