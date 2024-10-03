package dev.than0s.aluminium.features.post.presentation.screens.comments

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.SnackbarController
import dev.than0s.aluminium.features.post.domain.data_class.Comment
import dev.than0s.aluminium.features.post.domain.use_cases.AddCommentUseCase
import dev.than0s.aluminium.features.post.domain.use_cases.GetCommentFlowUseCase
import dev.than0s.aluminium.features.post.domain.use_cases.RemoveCommentUseCase
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCommentFlowUseCase: GetCommentFlowUseCase,
    private val addCommentUseCase: AddCommentUseCase,
    private val removeCommentUseCase: RemoveCommentUseCase,
) : ViewModel() {

    var commentFlow = emptyFlow<List<Comment>>()
    val postId = savedStateHandle.get<String>("postId")!!
    var currentComment by mutableStateOf("")

    init {
        loadComments()
    }

    private fun loadComments() {
        viewModelScope.launch {

            when (val result = getCommentFlowUseCase.invoke(postId)) {
                is Either.Left -> {
                    SnackbarController.showSnackbar(result.value.message)
                }

                is Either.Right -> {
                    commentFlow = result.value
                }
            }
        }
    }

    fun onCurrentCommentChange(value: String) {
        currentComment = value
    }

    fun onAddCommentClick() {
        viewModelScope.launch {
            val comment = Comment(
                message = currentComment,
                postId = postId
            )
            when (val result = addCommentUseCase.invoke(comment)) {
                is Either.Left -> {
                    SnackbarController.showSnackbar(result.value.message)
                }

                is Either.Right -> {
                    SnackbarController.showSnackbar("Comment added successfully")
                    currentComment = ""
                }
            }
        }
    }

    fun onRemoveCommentClick(comment: Comment) {
        viewModelScope.launch {
            when (val result = removeCommentUseCase.invoke(comment)) {
                is Either.Left -> {
                    SnackbarController.showSnackbar(result.value.message)
                }

                is Either.Right -> {
                    SnackbarController.showSnackbar("Comment removed successfully")
                }
            }
        }
    }
}