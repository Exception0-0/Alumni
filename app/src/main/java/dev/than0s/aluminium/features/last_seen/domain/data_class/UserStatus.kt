package dev.than0s.aluminium.features.last_seen.domain.data_class

data class UserStatus(
    val isOnline: Boolean = false,
    val lastSeen: Long = 0
)
