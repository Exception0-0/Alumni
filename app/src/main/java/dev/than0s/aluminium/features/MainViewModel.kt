package dev.than0s.aluminium.features

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.than0s.aluminium.core.SnackbarController
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor() : ViewModel() {
    val snackbarHostState = SnackbarHostState()

    init {
        listenSnackbarChannel()
    }

    private fun listenSnackbarChannel() {
        viewModelScope.launch {
            SnackbarController.channel.collect { message ->
                snackbarHostState.showSnackbar(message)
            }
        }
    }
}