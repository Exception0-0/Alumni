package dev.than0s.aluminium.core

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object SnackbarController {
    private val _channel = Channel<String>()
    val channel = _channel.receiveAsFlow()

    suspend fun showSnackbar(message: String) {
        _channel.send(message)
    }
}