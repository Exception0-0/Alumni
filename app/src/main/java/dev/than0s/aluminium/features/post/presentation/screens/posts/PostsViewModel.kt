package dev.than0s.aluminium.features.post.presentation.screens.posts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.core.presentation.utils.SnackbarController
import dev.than0s.aluminium.core.presentation.utils.SnackbarEvent
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.core.domain.data_class.Like
import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.domain.use_case.AddLikeUseCase
import dev.than0s.aluminium.features.post.domain.use_cases.DeletePostUseCase
import dev.than0s.aluminium.core.domain.use_case.GetCurrentUserLikeStatusUseCase
import dev.than0s.aluminium.core.domain.use_case.GetPostsUseCase
import dev.than0s.aluminium.core.domain.use_case.GetUserUseCase
import dev.than0s.aluminium.core.domain.use_case.RemoveLikeUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPostUseCase: GetPostsUseCase,
    private val addLikeUseCase: AddLikeUseCase,
    private val removeLikeUseCase: RemoveLikeUseCase,
    private val getUserUserCase: GetUserUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val getCurrentUserLikeStatusUseCase: GetCurrentUserLikeStatusUseCase,
) : ViewModel() {

    private val postScreenArgs = savedStateHandle.toRoute<Screen.PostsScreen>()
    val userMap = mutableStateMapOf<String, User>()
    val likeMap = mutableStateMapOf<String, Like?>()
    var screenState by mutableStateOf(PostsState())

    init {
        loadPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch {
            screenState = screenState.copy(isLoading = true)

            when (val result = getPostUseCase(postScreenArgs.userId)) {
                is Resource.Success -> {
                    screenState = screenState.copy(
                        postList = result.data!!
                    )
                }

                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError()
                        )
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
                    likeMap.getLike(postId)
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
                    likeMap.remove(postId)
                    likeMap.getLike(postId)
                }
            }
        }
    }

    private fun SnapshotStateMap<String, User>.getUser(userId: String) {
        if (!containsKey(userId)) {
            viewModelScope.launch {
                when (val result = getUserUserCase(userId)) {
                    is Resource.Error -> {
                        // TODO: do something on error
                    }

                    is Resource.Success -> {
                        this@getUser[userId] = result.data!!
                    }
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

    private fun onPostDeleteClick(postId: String) {
        viewModelScope.launch {
            when (val result = deletePostUseCase(postId)) {
                is Resource.Success -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = UiText.StringResource(R.string.post_delete_successfully)
                        )
                    )
                    loadPosts()
                }

                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError()
                        )
                    )
                }
            }
        }
    }

    fun onEvent(event: PostsEvents) {
        when (event) {
            is PostsEvents.OnLikeClick -> {
                if (event.hasLike) {
                    removeLike(event.postId)
                } else {
                    addLike(event.postId)
                }
            }

            is PostsEvents.OnPostDeleteClick -> {
                onPostDeleteClick(event.postId)
            }

            is PostsEvents.GetUser -> {
                userMap.getUser(event.userId)
            }

            is PostsEvents.GetLike -> {
                likeMap.getLike(event.postId)
            }

            is PostsEvents.LoadPosts -> {
                loadPosts()
            }
        }
    }
}