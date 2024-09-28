package dev.than0s.aluminium.features.post.presentation.screens.my_posts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.SnackbarController
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.use_cases.AddLikeUseCase
import dev.than0s.aluminium.features.post.domain.use_cases.DeletePostUseCase
import dev.than0s.aluminium.features.post.domain.use_cases.GetPostFlowUseCase
import dev.than0s.aluminium.features.post.domain.use_cases.RemoveLikeUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPostViewModel @Inject constructor(
    private val deletePostUseCase: DeletePostUseCase,
    private val getPostFlowUseCase: GetPostFlowUseCase,
    private val addLikeUseCase: AddLikeUseCase,
    private val removeLikeUseCase: RemoveLikeUseCase,
) : ViewModel() {
    var postFlow = emptyFlow<List<Post>>()

    init {
        loadPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch {
            // don't use firebase in presentation layer directly. it should be call in data layer
            when (val result = getPostFlowUseCase.invoke(Firebase.auth.currentUser!!.uid)) {
                is Either.Right -> {
                    postFlow = result.value
                }

                is Either.Left -> {
                    println("${result.value}")
                }
            }
        }
    }

    fun onPostDeleteClick(id: String) {
        viewModelScope.launch {
            when (val result = deletePostUseCase.invoke(id)) {
                is Either.Right -> {
                    SnackbarController.showSnackbar("Post delete successfully")
                }

                is Either.Left -> {
                    SnackbarController.showSnackbar(result.value.message)
                }
            }
        }
    }

    fun onLikeClick(id: String, hasLiked: Boolean, onSuccessfulLike: () -> Unit) {
        viewModelScope.launch {
            val useCase = if (hasLiked) removeLikeUseCase else addLikeUseCase

            when (val result = useCase.invoke(id)) {
                is Either.Left -> {
                    SnackbarController.showSnackbar(result.value.message)
                }

                is Either.Right -> {
                    onSuccessfulLike()
                }
            }
        }
    }
}