package dev.than0s.aluminium.core.data_class

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.Exclude
import java.net.URL

data class User(
    val id: String = Firebase.auth.currentUser?.uid ?: "",
    val profileImage: Uri = Uri.EMPTY,
    val firstName: String = "",
    val lastName: String = "",
    val bio: String = "",
)