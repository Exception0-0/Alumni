package dev.than0s.aluminium.features.admin.data.data_source

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.dataObjects
import dev.than0s.aluminium.features.admin.domain.data_class.RequestForm
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface RequestDataSource {
    val requestList: Flow<List<RequestForm>>
    suspend fun setRequest(form: RequestForm)
}

class RequestDataSourceImple @Inject constructor(private val store: FirebaseFirestore) :
    RequestDataSource {
    override val requestList: Flow<List<RequestForm>>
        get() = try {
            store.collection(registrationRequests)
                .whereEqualTo("status.approvalStatus", null)
                .dataObjects()
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }

    override suspend fun setRequest(form: RequestForm) {
        try {
            store.collection(registrationRequests).document(form.id).set(form).await()
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }
}

const val registrationRequests = "registration_requests"