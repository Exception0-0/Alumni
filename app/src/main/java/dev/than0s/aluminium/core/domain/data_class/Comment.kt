package dev.than0s.aluminium.core.domain.data_class

data class Comment(
    val id: String = "",
    val postId: String = "",
    val userId: String = "",
    val message: String = "",
    val timestamp: Long = 0
)
