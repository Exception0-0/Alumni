package dev.than0s.aluminium.features.profile.data.mapper

import android.net.Uri
import dev.than0s.aluminium.core.domain.data_class.User

fun User.toRemoteUser(profileImageUri: Uri?, coverImageUri: Uri?): RemoteUser = RemoteUser(
    firstName = firstName,
    lastName = lastName,
    bio = bio,
    profileImageUri = profileImageUri.toString(),
    coverImageUri = coverImageUri.toString()
)

fun RemoteUser.toUser(userId: String): User = User(
    id = userId,
    firstName = firstName,
    lastName = lastName,
    bio = bio,
    profileImage = Uri.parse(profileImageUri),
    coverImage = Uri.parse(coverImageUri)
)

data class RemoteUser(
    val firstName: String = "",
    val lastName: String = "",
    val bio: String = "",
    val profileImageUri: String? = null,
    val coverImageUri: String? = null
)