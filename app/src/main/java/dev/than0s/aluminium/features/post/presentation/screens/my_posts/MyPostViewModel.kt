package dev.than0s.aluminium.features.post.presentation.screens.my_posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.use_cases.DeletePostDocUseCase
import dev.than0s.aluminium.features.post.domain.use_cases.DeletePostFileUseCase
import dev.than0s.aluminium.features.post.domain.use_cases.GetMyPostFlowUseCase
import dev.than0s.aluminium.features.post.domain.use_cases.GetPostFIleUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPostViewModel @Inject constructor(
    private val getPostFlowUseCase: GetMyPostFlowUseCase,
    private val getPostFileUseCase: GetPostFIleUseCase,
    private val deletePostUseCase: DeletePostDocUseCase,
    private val deletePostFileUseCase: DeletePostFileUseCase
) : ViewModel() {
    var postsFlow: Flow<List<Post>> = emptyFlow()

    init {
        loadPosts()
    }

    fun loadPosts() {
        viewModelScope.launch {
            when (val result = getPostFlowUseCase.invoke(Unit)) {
                is Either.Right -> {
                    postsFlow = result.value.map { list ->
                        list.map { post ->
                            when (val fileResult = getPostFileUseCase.invoke(post.id)) {
                                is Either.Right -> {
                                    post.copy(file = fileResult.value)
                                }

                                is Either.Left -> {
                                    println("error: ${fileResult.value}")
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

    fun onPostDeleteClick(id: String) {
        viewModelScope.launch {
            when (val docResult = deletePostFileUseCase.invoke(id)) {
                is Either.Right -> {
                    when (val fileResult = deletePostUseCase.invoke(id)) {
                        is Either.Right -> {
                            println("success")
                        }

                        is Either.Left -> {
                            println("error: ${fileResult.value}")
                        }
                    }
                }

                is Either.Left -> {
                    println("error ${docResult.value.message}")
                }
            }

        }
    }
}