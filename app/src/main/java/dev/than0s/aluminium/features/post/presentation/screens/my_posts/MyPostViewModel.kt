package dev.than0s.aluminium.features.post.presentation.screens.my_posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.use_cases.DeletePostUseCase
import dev.than0s.aluminium.features.post.domain.use_cases.GetPostFlowUseCase
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
    private val getPostFlowUseCase: GetPostFlowUseCase
) : ViewModel() {
    //    private val _postsFlow = MutableStateFlow(emptyList<Post>())
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
            when (val docResult = deletePostUseCase.invoke(id)) {
                is Either.Right -> {
                    println("done")
                }

                is Either.Left -> {
                    println("error ${docResult.value.message}")
                }
            }

        }
    }
}