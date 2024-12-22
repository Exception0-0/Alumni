package dev.than0s.aluminium.features.post.presentation.screens.comments

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.Screen
import dev.than0s.aluminium.core.SnackbarController
import dev.than0s.aluminium.core.SnackbarEvent
import dev.than0s.aluminium.core.UiText
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.features.post.domain.data_class.Comment
import dev.than0s.aluminium.features.post.domain.use_cases.AddCommentUseCase
import dev.than0s.aluminium.features.post.domain.use_cases.GetCommentUseCase
import dev.than0s.aluminium.features.post.domain.use_cases.RemoveCommentUseCase
import dev.than0s.aluminium.core.domain.use_case.GetUserUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCommentUseCase: GetCommentUseCase,
    private val addCommentUseCase: AddCommentUseCase,
    private val removeCommentUseCase: RemoveCommentUseCase,
    private val getUserUserCase: GetUserUseCase
) : ViewModel() {

    private val commentsScreenArgs = savedStateHandle.toRoute<Screen.CommentsScreen>()

    val userMap = mutableStateMapOf<String, User>()

    var screenState by mutableStateOf(CommentState())

    init {
        // loading post id in comment param
        screenState = screenState.copy(
            comment = screenState.comment.copy(
                postId = commentsScreenArgs.postId
            )
        )

        loadComments()
    }

    private fun loadComments() {
        viewModelScope.launch {
            screenState = screenState.copy(isLoading = true)
            when (val result = getCommentUseCase(commentsScreenArgs.postId)) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Success -> {
                    screenState = screenState.copy(
                        commentList = result.data!!
                    )
                }
            }
            screenState = screenState.copy(isLoading = false)
        }
    }

    private fun onCommentChanged(value: String) {
        screenState = screenState.copy(
            comment = screenState.comment.copy(
                message = value
            )
        )
    }

    private fun onAddCommentClick() {
        viewModelScope.launch {
            screenState = screenState.copy(isCommentAdding = true)

            val addCommentResult = addCommentUseCase.invoke(screenState.comment)

            addCommentResult.messageError?.let {
                screenState = screenState.copy(
                    commentError = it
                )
            }

            when (addCommentResult.result) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = addCommentResult.result.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Success -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = UiText.StringResource(R.string.successfully_comment_added)
                        )
                    )

                    // removing message from comment box
                    screenState = screenState.copy(
                        comment = screenState.comment.copy(
                            message = ""
                        )
                    )
                }

                null -> {}
            }
            screenState = screenState.copy(isCommentAdding = false)
        }
    }

    private fun onRemoveCommentClick(comment: Comment) {
        viewModelScope.launch {
            when (val result = removeCommentUseCase(comment)) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Success -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = UiText.StringResource(R.string.successfully_comment_removed)
                        )
                    )
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

    fun onEvent(event: CommentEvents) {
        when (event) {
            is CommentEvents.OnCommentChanged -> {
                onCommentChanged(event.value)
            }

            is CommentEvents.OnAddCommentClick -> {
                onAddCommentClick()
            }

            is CommentEvents.OnCommentRemovedClick -> {
                onRemoveCommentClick(event.comment)
            }

            is CommentEvents.LoadComments -> {
                loadComments()
            }

            is CommentEvents.GetUser -> {
                userMap.getUser(event.userId)
            }
        }
    }
}