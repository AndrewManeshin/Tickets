package com.darkular.tickets.data.group.groups

import com.darkular.tickets.core.Delay
import com.darkular.tickets.core.FirebaseDatabaseProvider
import com.darkular.tickets.core.Save
import com.darkular.tickets.data.chat.MessageData
import com.darkular.tickets.data.chats.ChatData
import com.darkular.tickets.data.chats.ChatInfoData
import com.darkular.tickets.data.group.GroupInitial
import com.darkular.tickets.data.login.UserInitial
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface GroupsRepository : Save<String> {

    fun stopGettingUpdates()
    fun startGettingUpdates(callback: GroupsDataRealtimeUpdateCallback)
    suspend fun groupInfo(groupId: String): ChatInfoData

    class Base(//todo use cloudDataSource
        private val firebaseDatabaseProvider: FirebaseDatabaseProvider,
        private val groupIdContainer: Save<String>
    ) : GroupsRepository {

        private val myUid = Firebase.auth.currentUser!!.uid

        private var callback: GroupsDataRealtimeUpdateCallback =
            GroupsDataRealtimeUpdateCallback.Empty

        override fun stopGettingUpdates() {
            groupsDelay.clear()
            callback = GroupsDataRealtimeUpdateCallback.Empty
            firebaseDatabaseProvider.provideDatabase()
                .child("users-groups")
                .child(myUid)
                .removeEventListener(groupsEventListener)
            listenersMap.forEach { (groupId, listener) ->
                firebaseDatabaseProvider.provideDatabase()
                    .child("groups")
                    .child(groupId)
                    .removeEventListener(listener)
            }
        }

        private val groupsEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) =
                startGettingGroups(snapshot.children.mapNotNull { it.key })

            override fun onCancelled(error: DatabaseError) = Unit
        }

        override fun startGettingUpdates(callback: GroupsDataRealtimeUpdateCallback) {
            this.callback = callback
            firebaseDatabaseProvider.provideDatabase()
                .child("users-groups")
                .child(myUid)
                .addValueEventListener(groupsEventListener)
        }

        private fun startGettingGroups(groups: List<String>) {
            groups.forEach { groupId -> startListeningGroup(groupId) }
        }

        private fun startListeningGroup(groupId: String) {
            val listener = listener(groupId)
            firebaseDatabaseProvider.provideDatabase()
                .child("groups")
                .child(groupId)
                .child("messages")
                .addValueEventListener(listener)
        }

        private fun listener(groupId: String) = if (listenersMap.containsKey(groupId))
            listenersMap[groupId]!!
        else {
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val allMessages = snapshot.children.mapNotNull { message ->
                        message.getValue(MessageData.Base::class.java)
                    }
                    if (allMessages.isNotEmpty()) {
                        val notReadMessagesCount = allMessages.filter { messageData ->
                            messageData.userId != myUid && !messageData.wasRead
                        }.size
                        val lastMessage = allMessages.last()
                        val chatData = ChatData.Group(groupId, lastMessage, notReadMessagesCount)
                        processGroup(groupId, chatData)
                    }
                }

                override fun onCancelled(error: DatabaseError) = Unit
            }
            listenersMap[groupId] = listener
            listener
        }

        private val groupsDelay = Delay<List<ChatData>>(200) { callback.updateGroups(it) }

        private fun processGroup(groupId: String, chatData: ChatData) {
            groupsMap[groupId] = chatData
            val groups = groupsMap.map { it.value }
            groupsDelay.add(groups)
        }

        private val groupsMap = mutableMapOf<String, ChatData>()
        private val listenersMap = mutableMapOf<String, ValueEventListener>()

        override fun save(data: String) = groupIdContainer.save(data)

        private val groups = mutableMapOf<String, ChatInfoData>()

        override suspend fun groupInfo(groupId: String) = if (groups.containsKey(groupId))
            groups[groupId]!!
        else {
            val group = firebaseDatabaseProvider.provideDatabase()
                .child("groups")
                .child(groupId)
                .get()
            val groupInitial = handleResult(group)
            val groupInfo = ChatInfoData.Group(
                groupId,
                groupInitial.name,
                groupInitial.photoUrl
            )
            groups[groupId] = groupInfo
            groupInfo
        }

        private suspend fun handleResult(user: Task<DataSnapshot>) =
            suspendCoroutine<GroupInitial> { cont ->
                user.addOnSuccessListener { cont.resume(it.getValue(GroupInitial::class.java)!!) }
                    .addOnFailureListener { cont.resumeWithException(it) }
            }
    }
}

interface GroupsDataRealtimeUpdateCallback {
    fun updateGroups(groupDataList: List<ChatData>)

    object Empty : GroupsDataRealtimeUpdateCallback {
        override fun updateGroups(groupDataList: List<ChatData>) = Unit
    }
}