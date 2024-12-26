package dev.than0s.aluminium.features.profile.data.mapper

import com.google.firebase.firestore.DocumentId
import dev.than0s.aluminium.core.Course
import dev.than0s.aluminium.core.Role
import dev.than0s.aluminium.core.domain.data_class.AboutInfo

data class RemoteAboutInfo(
    @DocumentId
    val userId: String = "",
    val role: String = Role.Student.toString(),
    val course: String? = null,
    val batchFrom: String? = null,
    val batchTo: String? = null
)

fun RemoteAboutInfo.toAboutInfo(): AboutInfo = AboutInfo(
    userId = userId,
    role = Role.valueOf(role),
    course = course?.let { Course.valueOf(it) },
    batchTo = batchTo,
    batchFrom = batchFrom
)

fun AboutInfo.toRemoteAboutInfo(): RemoteAboutInfo = RemoteAboutInfo(
    userId = userId,
    role = role.toString(),
    course = course?.toString(),
    batchFrom = batchFrom,
    batchTo = batchTo
)