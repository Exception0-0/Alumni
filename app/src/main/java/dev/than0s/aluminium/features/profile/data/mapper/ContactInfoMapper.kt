package dev.than0s.aluminium.features.profile.data.mapper

import com.google.firebase.firestore.DocumentId
import dev.than0s.aluminium.core.domain.data_class.ContactInfo

data class RemoteContactInfo(
    @DocumentId
    val userId: String = "",
    val email: String? = null,
    val mobile: String? = null,
    val socialHandles: String? = null
)

fun RemoteContactInfo.toContactInfo(): ContactInfo = ContactInfo(
    userId = userId,
    email = email,
    mobile = mobile,
    socialHandles = socialHandles
)

fun ContactInfo.toRemoteContactInfo(): RemoteContactInfo = RemoteContactInfo(
    userId = userId,
    email = email,
    mobile = mobile,
    socialHandles = socialHandles
)