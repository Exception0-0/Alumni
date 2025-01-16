package dev.than0s.aluminium.core.presentation.utils

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.domain.use_case.GetUserUseCase
import dev.than0s.aluminium.features.profile.data.remote.ProfileDataSourceImple
import dev.than0s.aluminium.features.profile.data.repositories.ProfileRepositoryImple
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object UserProfile {
    // TODO: fix this with hilt
    private val getUserUseCase = GetUserUseCase(
        repository = ProfileRepositoryImple(
            dataSource = ProfileDataSourceImple(
                auth = FirebaseAuth.getInstance(),
                store = FirebaseFirestore.getInstance(),
                cloud = FirebaseStorage.getInstance(),
            )
        )
    )

    val userMap = mutableStateMapOf<String, User>()
    fun SnapshotStateMap<String, User>.getUser(userId: String) {
        if (!containsKey(userId)) {
            CoroutineScope(Dispatchers.Default).launch {
                when (val result = getUserUseCase(userId)) {
                    is Resource.Error -> {
                        Log.e("UserProfile", "getUser: ${result.uiText}")
                    }

                    is Resource.Success -> {
                        this@getUser[userId] = result.data!!
                    }
                }
            }
        }
    }
}