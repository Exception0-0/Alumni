package dev.than0s.aluminium.features.registration.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.toObjects
import com.google.firebase.storage.FirebaseStorage
import dev.than0s.aluminium.core.data.remote.ID_CARD_IMAGES
import dev.than0s.aluminium.core.data.remote.REGISTRATION_REQUESTS
import dev.than0s.aluminium.core.data.remote.STATUS_APPROVAL_STATUS
import dev.than0s.aluminium.core.data.remote.error.ServerException
import dev.than0s.aluminium.features.registration.data.mapper.RemoteRegistrationForm
import dev.than0s.aluminium.features.registration.data.mapper.toRegistrationForm
import dev.than0s.aluminium.features.registration.data.mapper.toRemoteRegistrationForm
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface RegisterDataSource {
    suspend fun addRegistrationRequest(form: RegistrationForm)
    suspend fun getRegistrationRequests(): List<RegistrationForm>
    suspend fun acceptRegistrationRequest(registrationRequestId: String)
    suspend fun rejectRegistrationRequest(registrationRequestId: String)
}

class RegisterDataSourceImple @Inject constructor(
    private val store: FirebaseFirestore,
    private val storage: FirebaseStorage
) :
    RegisterDataSource {

    override suspend fun addRegistrationRequest(form: RegistrationForm) {
        try {
            val idCardUri = form.idCardImage?.let {
                storage.reference.child("$ID_CARD_IMAGES/${form.id}")
                    .putFile(it)
                    .await()
                    .storage
                    .downloadUrl
                    .await()
            }

            store.collection(REGISTRATION_REQUESTS)
                .document(form.id)
                .set(
                    form.copy(idCardImage = idCardUri)
                        .toRemoteRegistrationForm()
                )
                .await()
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun getRegistrationRequests(): List<RegistrationForm> {
        return try {
            store.collection(REGISTRATION_REQUESTS)
                .get()
                .await()
                .toObjects<RemoteRegistrationForm>()
                .map { it.toRegistrationForm() }
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun acceptRegistrationRequest(registrationRequestId: String) {
        try {
            store.collection(REGISTRATION_REQUESTS)
                .document(registrationRequestId)
                .update(mapOf(STATUS_APPROVAL_STATUS to true))
                .await()
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun rejectRegistrationRequest(registrationRequestId: String) {
        try {
            store.collection(REGISTRATION_REQUESTS)
                .document(registrationRequestId)
                .update(mapOf(STATUS_APPROVAL_STATUS to false))
                .await()
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }
}

