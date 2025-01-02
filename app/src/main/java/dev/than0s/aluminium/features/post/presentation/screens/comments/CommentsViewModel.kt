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
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.domain.use_case.GetUserUseCase
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.core.presentation.utils.SnackbarController
import dev.than0s.aluminium.core.presentation.utils.SnackbarEvent
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.features.post.domain.use_cases.AddCommentUseCase
import dev.than0s.aluminium.features.post.domain.use_cases.GetCommentUseCase
import dev.than0s.aluminium.features.post.domain.use_cases.RemoveCommentUseCase
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
        initScreenState()
        loadComments()
    }

    private fun initScreenState() {
        screenState = screenState.copy(
            comment = screenState.comment.copy(
                postId = commentsScreenArgs.postId
            )
        )
    }

    private fun removeCommentMessage() {
        screenState = screenState.copy(
            comment = screenState.comment.copy(
                message = ""
            )
        )
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

            addCommentResult.let {
                screenState = screenState.copy(
                    commentError = it.messageError
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
                    removeCommentMessage()
                    loadComments()
                }

                null -> {}
            }
            screenState = screenState.copy(isCommentAdding = false)
        }
    }

    private fun deleteComment() {
        viewModelScope.launch {
            screenState = screenState.copy(isDeleting = true)

            val comment = screenState.commentList.find { it.id == screenState.deleteCommentId!! }!!
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
                    loadComments()
                }
            }
            dismissCommentDeleteDialog()
            screenState = screenState.copy(isDeleting = false)
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

    private fun showCommentDeleteDialog(commentId: String) {
        screenState = screenState.copy(
            deleteCommentId = commentId
        )
    }

    private fun dismissCommentDeleteDialog() {
        screenState = screenState.copy(
            deleteCommentId = null
        )
    }

    fun onEvent(event: CommentEvents) {
        when (event) {
            is CommentEvents.OnCommentChanged -> {
                onCommentChanged(event.value)
            }

            is CommentEvents.OnAddCommentClick -> {
                onAddCommentClick()
            }

            is CommentEvents.LoadComments -> {
                loadComments()
            }

            is CommentEvents.GetUser -> {
                userMap.getUser(event.userId)
            }

            CommentEvents.DeleteComment -> {
                deleteComment()
            }

            CommentEvents.DismissCommentDeleteDialog -> {
                dismissCommentDeleteDialog()
            }

            is CommentEvents.ShowCommentDeleteDialog -> {
                showCommentDeleteDialog(event.postId)
            }
        }
    }

}