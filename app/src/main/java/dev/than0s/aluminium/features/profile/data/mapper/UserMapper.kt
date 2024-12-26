package dev.than0s.aluminium.features.profile.data.mapper

import android.net.Uri
import com.google.firebase.firestore.DocumentId
import dev.than0s.aluminium.core.domain.data_class.User

fun User.toRemoteUser(): RemoteUser = RemoteUser(
    firstName = firstName,
    lastName = lastName,
    bio = bio,
    profileImageUri = profileImage.toString(),
    coverImageUri = coverImage.toString()
)

fun RemoteUser.toUser(): User = User(
    id = id,
    firstName = firstName,
    lastName = lastName,
    bio = bio,
    profileImage = profileImageUri?.let { Uri.parse(it) },
    coverImage = coverImageUri?.let { Uri.parse(it) }
)

data class RemoteUser(
    @DocumentId
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val bio: String = "",
    val profileImageUri: String? = null,
    val coverImageUri: String? = null
)