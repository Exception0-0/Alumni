package dev.than0s.aluminium.features.post.presentation.screens.all_posts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.data_class.User
import dev.than0s.aluminium.features.post.domain.use_cases.GetAllPostFlowUseCase
import dev.than0s.aluminium.features.post.domain.use_cases.GetPostFIleUseCase
import dev.than0s.aluminium.features.post.domain.use_cases.GetUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllPostsScreenViewModel @Inject constructor(
    private val getAllPostUseCase: GetAllPostFlowUseCase,
    private val getPostFileUseCase: GetPostFIleUseCase,
    private val getUserUseCase: GetUserUseCase
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

    fun getUser(userId: String): Flow<User> {
        println("here")
        return flow {
            when (val result = getUserUseCase.invoke(userId)) {
                is Either.Right -> {
                    println("collected")
                    emit(result.value)
                }

                is Either.Left -> {
                    println(result.value.message)
                }
            }
        }
    }
}