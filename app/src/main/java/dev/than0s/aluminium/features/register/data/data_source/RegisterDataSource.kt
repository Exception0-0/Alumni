package dev.than0s.aluminium.features.register.data.data_source

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import dev.than0s.aluminium.core.data_class.RegistrationForm
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

const val register = "register"
