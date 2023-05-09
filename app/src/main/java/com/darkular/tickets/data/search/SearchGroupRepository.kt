package com.darkular.tickets.data.search

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.darkular.tickets.core.FirebaseDatabaseProvider
import com.darkular.tickets.core.Save
import com.darkular.tickets.data.group.GroupInitial
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface SearchGroupRepository : Save<String> {
    suspend fun search(query: String): List<SearchData>
    suspend fun initChat(groupId: String): Boolean

    class Base(
        private val firebaseDatabaseProvider: FirebaseDatabaseProvider,
        private val groupIdContainer: Save<String>
    ) : SearchGroupRepository {

        override suspend fun search(query: String): List<SearchData> {
            val groups = firebaseDatabaseProvider.provideDatabase()
                .child("groups")
                .orderByChild("name")
                .equalTo(query)
            return handleResult(groups).map { (key, data) ->
                SearchData.Group(key, data.name)
            }
        }

        private suspend fun handleResult(users: Query) =
            suspendCoroutine<List<Pair<String, GroupInitial>>> { cont ->
                users.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) =
                        snapshot.children.mapNotNull {
                            Pair(it.key!!, it.getValue(GroupInitial::class.java)!!)
                        }.let { cont.resume(it) }

                    override fun onCancelled(error: DatabaseError) = Unit
                })
            }

        private val myUid = Firebase.auth.currentUser!!.uid

        override suspend fun initChat(groupId: String): Boolean = firebaseDatabaseProvider
            .provideDatabase().child("users-groups").run {
                var result = handleData(child(myUid).child(groupId).setValue(true))
                if (result) {
                    result = handleData(child(groupId).child(myUid).setValue(true))
                    if (result) save(groupId)
                }
                return result
            }

        private suspend fun handleData(data: Task<Void>) = suspendCoroutine<Boolean> { cont ->
            data.addOnSuccessListener { cont.resume(true) }
                .addOnFailureListener { cont.resume(false) }
        }

        override fun save(data: String) = groupIdContainer.save(data)
    }
}