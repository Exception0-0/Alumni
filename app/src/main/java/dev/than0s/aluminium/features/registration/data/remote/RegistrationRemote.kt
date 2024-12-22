package dev.than0s.aluminium.features.registration.data.remote

import android.net.Uri
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObjects
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import dev.than0s.aluminium.core.data.remote.ID_CARD_IMAGES
import dev.than0s.aluminium.core.data.remote.REGISTRATION_REQUESTS
import dev.than0s.aluminium.core.data.remote.error.ServerException
import dev.than0s.aluminium.core.isLocalUri
import dev.than0s.aluminium.features.registration.data.mapper.RemoteRegistrationForm
import dev.than0s.aluminium.features.registration.data.mapper.toRegistrationForm
import dev.than0s.aluminium.features.registration.data.mapper.toRemoteRegistrationForm
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm
import kotlinx.coroutines.flow.Flow
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
            var idCardUri: Uri? = null

            form.idCardImage?.let {
                idCardUri =
                    if (isLocalUri(it)) {
                        storage.reference.child("$ID_CARD_IMAGES/${form.id}")
                            .putFile(it)
                            .await()
                            .storage
                            .downloadUrl
                            .await()
                    } else it
            }

            store.collection(REGISTRATION_REQUESTS)
                .document(form.id)
                .set(
                    form.copy(idCardImage = idCardUri)
                        .toRemoteRegistrationForm()
                )
                .await()
        } catch (e: FirebaseException) {
            store.collection(REGISTRATION_REQUESTS)
                .document(form.id)
                .delete()
                .await()
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun registrationList(): List<RegistrationForm> {
        return try {
            store.collection(REGISTRATION_REQUESTS)
                .whereEqualTo("status.approvalStatus", null)
                .get()
                .await()
                .toObjects<RemoteRegistrationForm>()
                .map { it.toRegistrationForm() }
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }
}

