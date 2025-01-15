package dev.than0s.aluminium.features.chat.data.remote

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import dev.than0s.aluminium.core.data.remote.CHATS
import dev.than0s.aluminium.core.data.remote.USERS_ID
import dev.than0s.aluminium.core.data.remote.error.ServerException
import dev.than0s.aluminium.core.domain.util.generateUniqueId
import dev.than0s.aluminium.features.chat.data.mapper.RemoteChatGroup
import dev.than0s.aluminium.features.chat.data.mapper.toChatGroup
import dev.than0s.aluminium.features.chat.data.mapper.toRemoteChatGroup
import dev.than0s.aluminium.features.chat.domain.data_class.ChatGroup
import dev.than0s.aluminium.features.chat.domain.data_class.ChatMessage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface RemoteChat {
    suspend fun addMessage(groupId: String, message: ChatMessage)
    suspend fun getGroup(receiverUserId: String): ChatGroup
    suspend fun getGroups(): List<ChatGroup>
    fun getMessages(groupId: String): Flow<List<ChatMessage>>
}

class RemoteChatImple @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase,
    private val firestore: FirebaseFirestore
) : RemoteChat {
    override suspend fun addMessage(groupId: String, message: ChatMessage) {
        try {
            database.reference
                .child(CHATS)
                .child(groupId)
                .child(message.id)
                .setValue(message)
                .await()
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

    override fun getMessages(groupId: String): Flow<List<ChatMessage>> {
        return try {
            database.reference
                .child(CHATS)
                .child(groupId)
                .orderByChild("timestamp")
                .asFlow()
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun getGroup(receiverUserId: String): ChatGroup {
        return try {
            // checkout is group already created or not
            var chatGroup = firestore.collection(CHATS)
                .whereArrayContains(USERS_ID, listOf(auth.currentUser!!.uid, receiverUserId))
                .get()
                .await()
                .toObjects(RemoteChatGroup::class.java)
                .firstOrNull()
                ?.toChatGroup()

            // if chatGroup is null, create new chatGroup
            if (chatGroup == null) {
                chatGroup = ChatGroup(
                    id = generateUniqueId(),
                    usersId = listOf(auth.currentUser!!.uid, receiverUserId)
                )
                addGroup(chatGroup)
            }

            chatGroup
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun getGroups(): List<ChatGroup> {
        return try {
            firestore.collection(CHATS)
                .whereArrayContains(USERS_ID, auth.currentUser!!.uid)
                .get()
                .await()
                .toObjects(RemoteChatGroup::class.java)
                .map { it.toChatGroup() }
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

    private suspend fun addGroup(chatGroup: ChatGroup) {
        try {
            firestore.collection(CHATS)
                .document(chatGroup.id)
                .set(chatGroup.toRemoteChatGroup())
                .await()
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

    // chat gpt thank to help me :)
    private fun Query.asFlow(): Flow<List<ChatMessage>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val chatMessages = mutableListOf<ChatMessage>()
                snapshot.children.forEach { child ->
                    val id = child.key.orEmpty() // Use the key as the message ID
                    val message = child.child("message").getValue(String::class.java)!!
                    val userId = child.child("userId").getValue(String::class.java)!!
                    val timestamp = child.child("timestamp").getValue(Long::class.java)!!

                    // Create a ChatMessage object
                    val chatMessage = ChatMessage(
                        id = id,
                        userId = userId,
                        message = message,
                        timestamp = timestamp
                    )

                    chatMessages.add(chatMessage)
                }
                trySend(chatMessages)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        this@asFlow.addValueEventListener(listener)

        awaitClose { this@asFlow.removeEventListener(listener) }
    }
}