package dev.than0s.aluminium.core.data_class

data class AdminRequest(
    val status: Boolean = false,
    val authorized: Boolean,
    val email: String = "",
    val message: String = "",
)