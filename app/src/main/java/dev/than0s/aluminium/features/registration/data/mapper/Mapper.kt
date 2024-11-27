package dev.than0s.aluminium.features.registration.data.mapper

import android.net.Uri
import com.google.firebase.firestore.DocumentId
import dev.than0s.aluminium.core.Course
import dev.than0s.aluminium.core.Role
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun RegistrationForm.toRawRegistrationForm(): RawRegistrationForm = RawRegistrationForm(
    id = id,
    role = role,
    collegeId = collegeId,
    email = email,
    firstName = firstName,
    middleName = middleName,
    lastName = lastName,
    batchFrom = batchFrom,
    batchTo = batchTo,
    course = course,
    status = status
)

suspend fun Flow<List<RawRegistrationForm>>.toRegistrationForm(
    getIdCardImage: suspend (String) -> Uri?
): Flow<List<RegistrationForm>> {
    return map { it ->
        it.map {
            RegistrationForm(
                id = it.id,
                role = it.role,
                collegeId = it.collegeId,
                email = it.email,
                firstName = it.firstName,
                middleName = it.middleName,
                lastName = it.lastName,
                batchFrom = it.batchFrom,
                idCardImage = getIdCardImage(it.id),
                batchTo = it.batchTo,
                course = it.course,
                status = it.status
            )
        }
    }
}


data class RawRegistrationForm(
    @DocumentId val id: String = "",
    val role: Role = Role.Student,
    val collegeId: String? = null,
    val email: String = "",
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val batchFrom: String? = null,
    val batchTo: String? = null,
    val course: Course? = null,
    val status: RegistrationStatus = RegistrationStatus()
)