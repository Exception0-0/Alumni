package dev.than0s.aluminium.features.post.data.data_source

import android.net.Uri
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.dataObjects
import com.google.firebase.storage.FirebaseStorage
import dev.than0s.aluminium.features.post.data.mapper.toRawPost
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface PostDataSource {
    suspend fun addPost(post: Post)
    suspend fun getPost(id: String): Post
    suspend fun setPostFile(file: Uri, id: String)
    suspend fun getPostFile(id: String): Uri
    suspend fun getMyPostFlow(): Flow<List<Post>>
    suspend fun getAllPostFlow(): Flow<List<Post>>
}

class PostDataSourceImple @Inject constructor(
    private val store: FirebaseFirestore,
    private val cloud: FirebaseStorage,
    private val auth: FirebaseAuth
) :
    PostDataSource {

    override suspend fun addPost(post: Post) {
        try {
            store.collection(POSTS).document(post.id).set(post.toRawPost()).await()
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

    override suspend fun getMyPostFlow(): Flow<List<Post>> {
        return try {
            store.collection(POSTS).whereEqualTo("userId", auth.currentUser!!.uid).dataObjects()
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun getAllPostFlow(): Flow<List<Post>> {
        return try {
            store.collection(POSTS).dataObjects()
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

    companion object {
        private const val POSTS = "posts"
    }
}