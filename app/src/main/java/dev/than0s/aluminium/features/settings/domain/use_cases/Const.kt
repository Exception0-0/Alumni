package dev.than0s.aluminium.features.settings.domain.use_cases

import com.google.firebase.Firebase
import com.google.firebase.auth.auth

const val profileImages = "profile_images"
val profileImagesPath = "$profileImages/${Firebase.auth.currentUser!!.uid}"
private val postsFilePath = "posts/${Firebase.auth.currentUser!!.uid}/"
fun getPostsFilePath(postId: String) = "$postsFilePath$postId"