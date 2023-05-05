package com.darkular.tickets.data.group.group

import com.darkular.tickets.core.FirebaseDatabaseProvider
import com.darkular.tickets.core.Read
import com.darkular.tickets.data.chat.InternetConnection
import com.darkular.tickets.data.chat.MessageData
import com.darkular.tickets.data.chat.MessagesData
import com.darkular.tickets.domain.chat.MessagesDataRealtimeUpdateCallback
import com.darkular.tickets.presentation.chat.ReadMessage
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface GroupRepository: ReadMessage {

    suspend fun sendMessage(message: String): Boolean
    fun startGettingUpdates(dataCallback: MessagesDataRealtimeUpdateCallback)
    fun stopGettingUpdates()

    class Base(
        private val firebaseDatabaseProvider: FirebaseDatabaseProvider,
        private val internetConnection: InternetConnection,
        private val groupIdContainer: Read<String>
    ) : GroupRepository {

        private val myUid = Firebase.auth.currentUser!!.uid
        private val groupId by lazy { groupIdContainer.read() }

        private val eventListener = object : ValueEventListener {

            private var lastCount = 0 //TODO something strange, resolve double call of listener

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.children.count() >= lastCount) {

                    val data = snapshot.children.mapNotNull { item ->
                        Pair(item.key!!, item.getValue(MessageData.Base::class.java)!!)
                    }
                    if (data.isNotEmpty())
                        callback.updateMessages(MessagesData.Success(data))

                    lastCount = snapshot.children.count()
                }
            }

            override fun onCancelled(error: DatabaseError) = Unit
        }

        private var callback: MessagesDataRealtimeUpdateCallback =
            MessagesDataRealtimeUpdateCallback.Empty

        override suspend fun sendMessage(message: String): Boolean {
            if (!internetConnection.isConnect()) throw IllegalStateException()

            val group = groupReference().push()
            val result = group.setValue(MessageData.Base(myUid, message, Firebase.auth.currentUser!!.photoUrl!!.toString()))
            return handle(result)
        }

        override fun startGettingUpdates(dataCallback: MessagesDataRealtimeUpdateCallback) {
            callback = dataCallback
            groupReference().addValueEventListener(eventListener)
        }

        override fun stopGettingUpdates() {
            groupReference().removeEventListener(eventListener)
            callback = MessagesDataRealtimeUpdateCallback.Empty
        }

        override fun readMessage(id: String) {
            groupReference().child(id).child("wasRead").setValue(true)
        }

        private suspend fun handle(result: Task<Void>) = suspendCoroutine<Boolean> { cont ->
            result.addOnSuccessListener { cont.resume(true) }
                .addOnFailureListener { cont.resumeWithException(it) }
        }

        private fun groupReference() = firebaseDatabaseProvider.provideDatabase().child("groups").child(groupId).child("messages")
    }
}