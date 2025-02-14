package dev.than0s.aluminium.features.chat.data.remote

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.snapshots
import dev.than0s.aluminium.core.data.remote.CHATS
import dev.than0s.aluminium.core.data.remote.HISTORY
import dev.than0s.aluminium.core.data.remote.TIMESTAMP
import dev.than0s.aluminium.core.data.remote.error.ServerException
import dev.than0s.aluminium.features.chat.domain.data_class.ChatGroup
import dev.than0s.aluminium.features.chat.domain.data_class.ChatMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
                .snapshots
                .map { snapshot ->
                    snapshot.children.mapNotNull {
                        it.getValue(ChatGroup::class.java)
                    }
                }
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
                .orderByChild(TIMESTAMP)
                .snapshots
                .map { snapshot ->
                    snapshot.children.mapNotNull {
                        it.getValue(ChatMessage::class.java)
                    }
                }
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

    // below code is business logic it should present in domain layer not in data layer
    private fun getGroupId(receiverId: String): String {
        val sortedList = listOf(auth.currentUser!!.uid, receiverId)
            .sorted()
        return "${sortedList[0]}_${sortedList[1]}"
    }
}