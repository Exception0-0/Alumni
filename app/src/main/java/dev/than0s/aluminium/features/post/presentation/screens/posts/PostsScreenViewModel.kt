package dev.than0s.aluminium.features.post.presentation.screens.posts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.Screen
import dev.than0s.aluminium.core.SnackbarController
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.data_class.User
import dev.than0s.aluminium.features.post.domain.use_cases.AddLikeUseCase
import dev.than0s.aluminium.features.post.domain.use_cases.DeletePostUseCase
import dev.than0s.aluminium.features.post.domain.use_cases.GetPostsUseCase
import dev.than0s.aluminium.features.post.domain.use_cases.GetUserProfileUseCase
import dev.than0s.aluminium.features.post.domain.use_cases.HasUserLikedPostUseCase
import dev.than0s.aluminium.features.post.domain.use_cases.RemoveLikeUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPostUseCase: GetPostsUseCase,
    private val addLikeUseCase: AddLikeUseCase,
    private val removeLikeUseCase: RemoveLikeUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val hasUserLikedPostUseCase: HasUserLikedPostUseCase,
    private val deletePostUseCase: DeletePostUseCase,
) : ViewModel() {

    val postScreenArgs = savedStateHandle.toRoute<Screen.PostsScreen>()
    var isPostListLoading by mutableStateOf(false)

    private val userProfiles = mutableMapOf<String, User>()

    var postsList: List<Post> by mutableStateOf(emptyList())
        private set

    init {
        loadPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch {
            isPostListLoading = true
            when (val result = getPostUseCase.invoke(postScreenArgs.userId)) {
                is Either.Right -> {
                    postsList = result.value.map { post ->
                        post.copy(
                            isLiked = getLikeStatus(post.id) ?: false
                        )
                    }
                }

                is Either.Left -> {
                    SnackbarController.showSnackbar(result.value.message)
                }
            }
            isPostListLoading = false
        }
    }

    fun onLikeClick(postId: String, hasLiked: Boolean, onSuccessfulLike: () -> Unit) {
        viewModelScope.launch {
            val useCase = if (hasLiked) removeLikeUseCase else addLikeUseCase

            when (val result = useCase.invoke(postId)) {
                is Either.Left -> {
                    SnackbarController.showSnackbar(result.value.message)
                }

                is Either.Right -> {
                    onSuccessfulLike()
                }
            }
        }
    }

    fun getUserProfile(userId: String): Flow<User> {
        return flow {
            if (!userProfiles.containsKey(userId)) {
                when (val result = getUserProfileUseCase.invoke(userId)) {
                    is Either.Left -> {
                        println("Error getting user profile")
                        return@flow
                    }

                    is Either.Right -> {
                        userProfiles[userId] = result.value
                    }
                }
            }
            emit(userProfiles[userId]!!)
        }
    }

    private suspend fun getLikeStatus(postId: String): Boolean? {
        return when (val result = hasUserLikedPostUseCase.invoke(postId)) {
            is Either.Left -> {
                println("Error getting user profile")
                return null
            }

            is Either.Right -> {
                result.value
            }
        }
    }

    fun onPostDeleteClick(id: String) {
        viewModelScope.launch {
            when (val result = deletePostUseCase.invoke(id)) {
                is Either.Right -> {
                    SnackbarController.showSnackbar("Post delete successfully")
                    loadPosts()
                }

                is Either.Left -> {
                    SnackbarController.showSnackbar(result.value.message)
                }
            }
        }
    }
}