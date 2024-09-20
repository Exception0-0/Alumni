package dev.than0s.aluminium.features.post.presentation.screens.all_posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.use_cases.GetAllPostFlowUseCase
import dev.than0s.aluminium.features.post.domain.use_cases.GetPostFIleUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllPostsScreenViewModel @Inject constructor(
    private val getAllPostUseCase: GetAllPostFlowUseCase,
    private val getPostFileUseCase: GetPostFIleUseCase
) : ViewModel() {
    var postsFlow: Flow<List<Post>> = emptyFlow()

    init {
        loadPosts()
    }

    fun loadPosts() {
        viewModelScope.launch {
            when (val result = getAllPostUseCase.invoke(Unit)) {
                is Either.Right -> {
                    postsFlow = result.value.map { list ->
                        list.map { post ->
                            when (val fileResult = getPostFileUseCase.invoke(post.id)) {
                                is Either.Right -> {
                                    post.copy(file = fileResult.value)
                                }

                                is Either.Left -> {
                                    println("error: ${fileResult.value} id: ${post.id}")
                                    post
                                }
                            }
                        }
                    }
                }

                is Either.Left -> {
                    println("${result.value}")
                }
            }
        }
    }
}