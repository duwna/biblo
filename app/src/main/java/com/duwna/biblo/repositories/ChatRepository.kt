package com.duwna.biblo.repositories

import android.net.Uri
import com.duwna.biblo.entities.database.Message
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import java.util.*

class ChatRepository : BaseRepository() {

    suspend fun insertMessage(idGroup: String, text: String, imgUri: Uri?) {
        val message = Message(firebaseUserId, text, Date())

        val ref = database.collection("groups")
            .document(idGroup)
            .collection("messages")

        val id = ref.add(message)
            .await()
            .id

        imgUri?.let {
            val imgUrl = uploadImg("messages", id, it)
            ref.document(id).update("imgUrl", imgUrl)
        }
    }

    suspend fun getMessagesList(idGroup: String): List<Message> {
        return database.collection("groups")
            .document(idGroup)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .get()
            .await()
            .documents
            .map { it.toObject<Message>()!!.apply { id = it.id } }
    }

    suspend fun deleteMessage(idGroup: String, idMessage: String) {
        database.collection("groups")
            .document(idGroup)
            .collection("messages")
            .document(idMessage)
            .delete()
            .await()
    }
}