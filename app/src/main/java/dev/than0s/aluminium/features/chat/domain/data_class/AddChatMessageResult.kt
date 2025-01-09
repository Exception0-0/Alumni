package dev.than0s.aluminium.features.chat.domain.data_class

import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.domain.error.Error

data class AddChatMessageResult(
    val messageError: Error? = null,
    val result: SimpleResource? = null,
)
