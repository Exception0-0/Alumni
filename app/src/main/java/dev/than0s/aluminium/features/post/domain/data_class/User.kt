package dev.than0s.aluminium.features.post.domain.data_class

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

data class User(
    val userId: String = Firebase.auth.currentUser!!.uid,
    val firstName: String = "",
    val lastName: String = "",
    val profileImage: Uri = Uri.EMPTY
)
