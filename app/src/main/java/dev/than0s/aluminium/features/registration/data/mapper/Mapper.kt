package dev.than0s.aluminium.features.registration.data.mapper

import android.net.Uri
import com.google.firebase.firestore.DocumentId
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationStatus

fun RegistrationForm.toRawRegistrationForm(): RawRegistrationForm = RawRegistrationForm(
    id = id,
    category = category,
    rollNo = rollNo,
    email = email,
    mobile = mobile,
    firstName = firstName,
    middleName = middleName,
    lastName = lastName,
    batchFrom = batchFrom,
    batchTo = batchTo,
    status = status
)

suspend fun List<RawRegistrationForm>.toRegistrationForm(
    getIdCardImage: suspend (String) -> Uri?
): List<RegistrationForm> {
    return map {
        RegistrationForm(
            id = it.id,
            category = it.category,
            rollNo = it.rollNo,
            email = it.email,
            mobile = it.mobile,
            firstName = it.firstName,
            middleName = it.middleName,
            lastName = it.lastName,
            batchFrom = it.batchFrom,
            idCardImage = getIdCardImage(it.id),
            batchTo = it.batchTo,
            status = it.status
        )
    }
}


data class RawRegistrationForm(
    @DocumentId val id: String = "",
    val category: String = "",
    val rollNo: String = "",
    val email: String = "",
    val mobile: String? = null,
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val batchFrom: String = "",
    val batchTo: String = "",
    val status: RegistrationStatus = RegistrationStatus()
)