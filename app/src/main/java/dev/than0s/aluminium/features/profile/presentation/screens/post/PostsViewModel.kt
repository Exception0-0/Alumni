package dev.than0s.aluminium.features.profile.presentation.screens.post

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.domain.use_case.GetPostsUseCase
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.core.presentation.utils.SnackbarController
import dev.than0s.aluminium.core.presentation.utils.SnackbarEvent
import dev.than0s.aluminium.core.presentation.utils.UiText
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPostsUseCase: GetPostsUseCase,
) : ViewModel() {
    private val postsScreenArgs = savedStateHandle.toRoute<Screen.ProfileTabScreen.PostsScreen>()
    var screenState by mutableStateOf(PostsState())

    init {
        getUserPosts()
    }

    private fun getUserPosts() {
        viewModelScope.launch {
            screenState = screenState.copy(isLoading = true)
            when (val result = getPostsUseCase(postsScreenArgs.userId)) {
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

    fun onEvent(event: PostsEvents) {
        when (event) {
            is PostsEvents.LoadPosts -> {
                getUserPosts()
            }
        }
    }
}