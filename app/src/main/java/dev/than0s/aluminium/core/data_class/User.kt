package dev.than0s.aluminium.core.data_class

import com.google.firebase.firestore.Exclude
import java.net.URL

data class User(
    val id: String = "",
    @Exclude val profileImage: URL = URL("https://example.com"),
    val firstName: String = "",
    val lastName: String = "",
    val bio: String = "",
)