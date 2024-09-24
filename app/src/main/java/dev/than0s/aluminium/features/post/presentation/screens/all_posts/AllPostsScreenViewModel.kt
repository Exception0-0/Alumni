package dev.than0s.aluminium.features.post.presentation.screens.all_posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.use_cases.GetPostFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllPostsScreenViewModel @Inject constructor(
    private val getAllPostUseCase: GetPostFlowUseCase,
) : ViewModel() {
    var postsFlow: Flow<List<Post>> = emptyFlow()

    init {
        loadPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch {
            when (val result = getAllPostUseCase.invoke(null)) {
                is Either.Right -> {
                    postsFlow = result.value
                }

                is Either.Left -> {
                    println("${result.value}")
                }
            }
        }
    }
}