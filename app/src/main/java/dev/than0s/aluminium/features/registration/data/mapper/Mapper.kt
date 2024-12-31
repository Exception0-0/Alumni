package dev.than0s.aluminium.features.registration.data.mapper

import android.net.Uri
import dev.than0s.aluminium.core.Course
import dev.than0s.aluminium.core.Role
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationStatus

fun RegistrationForm.toRemoteRegistrationForm(): RemoteRegistrationForm = RemoteRegistrationForm(
    id = id,
    role = role,
    collegeId = collegeId,
    email = email,
    firstName = firstName,
    middleName = middleName,
    lastName = lastName,
    batchFrom = batchFrom,
    batchTo = batchTo,
    idCardImage = idCardImage?.toString(),
    course = course,
    status = status
)

fun RemoteRegistrationForm.toRegistrationForm(): RegistrationForm = RegistrationForm(
    id = id,
    role = role,
    collegeId = collegeId,
    email = email,
    firstName = firstName,
    middleName = middleName,
    lastName = lastName,
    batchFrom = batchFrom,
    batchTo = batchTo,
    idCardImage = idCardImage?.run { Uri.parse(this) },
    course = course,
    status = status
)


data class RemoteRegistrationForm(
    val id: String = "",
    val role: Role = Role.Student,
    val collegeId: String = "",
    val email: String = "",
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val batchFrom: String? = null,
    val batchTo: String? = null,
    val course: Course? = null,
    val idCardImage: String? = null,
    val status: RegistrationStatus = RegistrationStatus()
)