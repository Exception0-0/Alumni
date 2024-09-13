package dev.than0s.aluminium.features.settings.presentation.screens.post_upload

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.data_class.Post
import javax.inject.Inject

@HiltViewModel
class PostUploadViewModel @Inject constructor() : ViewModel() {
    var post by mutableStateOf(Post())
    var fileUri by mutableStateOf<Uri>(Uri.EMPTY)
    var circularProgressIndicatorState by mutableStateOf(false)

    fun onTitleChange(title: String) {
        post = post.copy(title = title)
    }

    fun onDescriptionChange(description: String) {
        post = post.copy(description = description)
    }

    fun onFileUriChange(uri: Uri) {
        fileUri = uri
    }

    fun onUploadClick() {

    }
}