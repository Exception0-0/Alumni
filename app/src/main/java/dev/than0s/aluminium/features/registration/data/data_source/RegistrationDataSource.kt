package dev.than0s.aluminium.features.registration.data.data_source

import android.net.Uri
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.toObjects
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import dev.than0s.aluminium.features.registration.data.mapper.RawRegistrationForm
import dev.than0s.aluminium.features.registration.data.mapper.toRawRegistrationForm
import dev.than0s.aluminium.features.registration.data.mapper.toRegistrationForm
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface RegisterDataSource {
    suspend fun setRegistration(form: RegistrationForm)
    suspend fun registrationList(): List<RegistrationForm>
}

class RegisterDataSourceImple @Inject constructor(
    private val store: FirebaseFirestore,
    private val storage: FirebaseStorage
) :
    RegisterDataSource {

    override suspend fun setRegistration(form: RegistrationForm) {
        try {
            store.collection(registrationRequests).document(form.id)
                .set(form.toRawRegistrationForm()).await()
            form.idCardImage?.let {
                storage.reference.child("$idCardImages/${form.id}")
                    .putFile(it)
                    .await()
            }
        } catch (e: FirebaseException) {
            store.collection(registrationRequests).document(form.id).delete().await()
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun registrationList(): List<RegistrationForm> {
        return try {
            store.collection(registrationRequests)
                .whereEqualTo("status.approvalStatus", null)
                .get()
                .await()
                .toObjects<RawRegistrationForm>()
                .toRegistrationForm(::getIdCardImage)
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }

    private suspend fun getIdCardImage(formId: String): Uri? {
        return try {
            storage.reference
                .child("$idCardImages/${formId}")
                .downloadUrl
                .await()
        } catch (e: StorageException) {
            if (e.errorCode == StorageException.ERROR_OBJECT_NOT_FOUND) {
                return null
            }
            throw ServerException(e.message.toString())
        }
    }
}

const val registrationRequests = "registration_requests"
const val idCardImages = "id_card_images"