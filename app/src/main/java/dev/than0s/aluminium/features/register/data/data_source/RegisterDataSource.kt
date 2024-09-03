package dev.than0s.aluminium.features.register.data.data_source

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface RegisterDataSource {
    suspend fun register(form: RegistrationForm)
}

class FirebaseRegisterDataSourceImple @Inject constructor(private val store: FirebaseFirestore) :
    RegisterDataSource {
    override suspend fun register(form: RegistrationForm) {
        try {
            store.collection(register).add(form).await()
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }
}


data class RegistrationForm(
    private val approvalStatus: Boolean? = null,
    val category: String,
    val id: String,
    val email: String,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val batch: String,
)

const val register = "register"
