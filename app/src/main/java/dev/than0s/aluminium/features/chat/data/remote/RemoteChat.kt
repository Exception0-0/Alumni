package dev.than0s.aluminium.features.chat.data.remote

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import dev.than0s.aluminium.core.data.remote.CHATS
import dev.than0s.aluminium.core.data.remote.HISTORY
import dev.than0s.aluminium.core.data.remote.error.ServerException
import dev.than0s.aluminium.features.chat.domain.data_class.ChatGroup
import dev.than0s.aluminium.features.chat.domain.data_class.ChatMessage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface RemoteChat {
    val chatGroups: Flow<List<ChatGroup>>
    suspend fun addMessage(receiverId: String, message: ChatMessage)
    fun getMessages(receiverId: String): Flow<List<ChatMessage>>
    suspend fun getMessage(receiverId: String, messageId: String): ChatMessage
}

class RemoteChatImple @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase,
) : RemoteChat {
    override val chatGroups
        get() = try {
            database.reference
                .child(CHATS)
                .child(HISTORY)
                .child(auth.currentUser!!.uid)
                .asFlow<ChatGroup>()
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }

    override suspend fun addMessage(receiverId: String, message: ChatMessage) {
        try {
            database.reference
                .child(CHATS)
                .child(getGroupId(receiverId))
                .child(message.id)
                .setValue(message)
                .await()

            database.reference
                .child(CHATS)
                .child(HISTORY)
                .child(auth.currentUser!!.uid)
                .child(receiverId)
                .setValue(
                    ChatGroup(
                        receiverId = receiverId,
                        messageId = message.id
                    )
                )
                .await()

            database.reference
                .child(CHATS)
                .child(HISTORY)
                .child(receiverId)
                .child(auth.currentUser!!.uid)
                .setValue(
                    ChatGroup(
                        receiverId = auth.currentUser!!.uid,
                        messageId = message.id
                    )
                )
                .await()
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

    override fun getMessages(receiverId: String): Flow<List<ChatMessage>> {
        return try {
            database.reference
                .child(CHATS)
                .child(getGroupId(receiverId))
                .orderByChild("timestamp")
                .asFlow()
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun getMessage(receiverId: String, messageId: String): ChatMessage {
        return try {
            database.reference
                .child(CHATS)
                .child(getGroupId(receiverId))
                .child(messageId)
                .get()
                .await()
                .getValue(ChatMessage::class.java)!!
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    // chat gpt thank to help me :)
    private inline fun <reified T> Query.asFlow(): Flow<List<T>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = mutableListOf<T>()
                snapshot.children.forEach { child ->
                    val item = child.getValue(T::class.java)
                    if (item != null) {
                        items.add(item)
                    }
                }
                trySend(items)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        this@asFlow.addValueEventListener(listener)

        awaitClose { this@asFlow.removeEventListener(listener) }
    }

    private fun getGroupId(receiverId: String): String {
        val sortedList = listOf(auth.currentUser!!.uid, receiverId)
            .sorted()
        return "${sortedList[0]}_${sortedList[1]}"
    }
}