package dev.than0s.aluminium.core.data_class

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.ui.graphics.vector.ImageVector

data class User(
    val id: String = "",
    val profileImage: ImageVector = Icons.Default.AccountCircle,
    val firstName: String = "",
    val lastName: String = "",
    val bio: String = "",
)