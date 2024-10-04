package dev.than0s.aluminium.features.post.presentation.screens.posts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.SnackbarController
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.data_class.User
import dev.than0s.aluminium.features.post.domain.use_cases.AddLikeUseCase
import dev.than0s.aluminium.features.post.domain.use_cases.DeletePostUseCase
import dev.than0s.aluminium.features.post.domain.use_cases.GetAllPostsUseCase
import dev.than0s.aluminium.features.post.domain.use_cases.GetCurrentUserPostUseCase
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
    private val getAllPostUseCase: GetAllPostsUseCase,
    private val getCurrentUserPostUseCase: GetCurrentUserPostUseCase,
    private val addLikeUseCase: AddLikeUseCase,
    private val removeLikeUseCase: RemoveLikeUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val hasUserLikedPostUseCase: HasUserLikedPostUseCase,
    private val deletePostUseCase: DeletePostUseCase,
) : ViewModel() {

    // to handel "current_user_post_screen" and "all_post_screen"
    val userId = savedStateHandle.get<String>("userId")

    private val userProfiles = mutableMapOf<String, User>()
    private val likedPostsStatus = mutableMapOf<String, Boolean>()

    var postsList: List<Post> by mutableStateOf(emptyList())
        private set

    init {
        loadPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch {
            val userCase = if (userId != null) getCurrentUserPostUseCase else getAllPostUseCase

            when (val result = userCase.invoke(Unit)) {
                is Either.Right -> {
                    postsList = result.value
                }

                is Either.Left -> {
                    SnackbarController.showSnackbar(result.value.message)
                }
            }
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
                    likedPostsStatus[postId] = !hasLiked
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

    fun getPostLikeStatus(postId: String): Flow<Boolean> {
        return flow {
            if (!likedPostsStatus.containsKey(postId)) {
                when (val result = hasUserLikedPostUseCase.invoke(postId)) {
                    is Either.Left -> {
                        println("Error getting user profile")
                        return@flow
                    }

                    is Either.Right -> {
                        likedPostsStatus[postId] = result.value
                    }
                }
            }
            emit(likedPostsStatus[postId]!!)
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
}