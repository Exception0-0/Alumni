package dev.than0s.aluminium.features.registration.data.mapper

import android.net.Uri
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import dev.than0s.aluminium.core.Course
import dev.than0s.aluminium.core.Role
import dev.than0s.aluminium.core.data.remote.getFirebaseTimestamp
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationRequestStatus

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
    timestamp = getFirebaseTimestamp(timestamp),
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
    timestamp = timestamp.toDate().time,
    status = status
)

data class RemoteRegistrationForm(
    @DocumentId
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
    val timestamp: Timestamp = Timestamp.now(),
    val status: RegistrationRequestStatus = RegistrationRequestStatus()
)