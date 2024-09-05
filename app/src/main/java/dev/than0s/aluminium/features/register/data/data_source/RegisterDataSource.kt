package dev.than0s.aluminium.features.register.data.data_source

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.dataObjects
import dev.than0s.aluminium.core.data_class.RegistrationForm
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface RegisterDataSource {
    val requestsList: Flow<List<RegistrationForm>>
    suspend fun submit(form: RegistrationForm)
    suspend fun accept(form: RegistrationForm)
    suspend fun reject(form: RegistrationForm)
}

class FirebaseRegisterDataSourceImple @Inject constructor(private val store: FirebaseFirestore) :
    RegisterDataSource {
    override val requestsList: Flow<List<RegistrationForm>>
        get() = try {
            store.collection(registrationRequests).whereEqualTo("status.approvalStatus", null)
                .dataObjects()
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }

    override suspend fun submit(form: RegistrationForm) {
        try {
            store.collection(registrationRequests).add(form).await()
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun accept(form: RegistrationForm) {
        try {
            store.collection(registrationRequests).document(form.id).set(form).await()
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun reject(form: RegistrationForm) {
        try {
            store.collection(registrationRequests).document(form.id).set(form).await()
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }
}

const val registrationRequests = "registration_requests"