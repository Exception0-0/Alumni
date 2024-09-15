package dev.than0s.aluminium.features.post.data.data_source

import android.net.Uri
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.storage.FirebaseStorage
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface PostDataSource {
    suspend fun setPost(post: Post)
    suspend fun getPost(id: String): Post
    suspend fun setPostFile(file: Uri, id: String)
    suspend fun getPostFile(id: String): Uri
}

class PostDataSourceImple @Inject constructor(
    private val store: FirebaseFirestore,
    private val cloud: FirebaseStorage,
    private val auth: FirebaseAuth
) :
    PostDataSource {

    override suspend fun setPost(post: Post) {
        try {
            store.collection(POSTS).document(post.id).set(
                hashMapOf(
                    "userId" to post.userId,
                    "title" to post.title,
                    "description" to post.description,
                    "timestamp" to post.timestamp
                )
            )
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun getPost(id: String): Post {
        try {
            return store.collection(POSTS).document(id).get().await().toObject(Post::class.java)!!
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun setPostFile(file: Uri, id: String) {
        try {
            cloud.reference.child("$POSTS/${auth.currentUser!!.uid}/$id").putFile(file)
                .await()
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun getPostFile(id: String): Uri {
        return try {
            cloud.reference.child("$POSTS/${auth.currentUser!!.uid}/$id").downloadUrl.await()
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

    companion object {
        private const val POSTS = "posts"
    }
}