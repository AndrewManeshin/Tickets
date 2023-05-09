package com.darkular.tickets.data.group.create

import com.darkular.tickets.core.FirebaseDatabaseProvider
import com.darkular.tickets.data.group.GroupInitial
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface CreateGroupRepository {

    suspend fun saveGroupName(groupName: String): Boolean
    suspend fun fetchGroups(): List<Pair<String, String>>
    suspend fun createGroup(filmId: String, photoUrl: String): Boolean

    class Base(
        private val firebaseDatabaseProvider: FirebaseDatabaseProvider,
        private val groupNameContainer: GroupNameContainer
    ) : CreateGroupRepository {

        private val uid = Firebase.auth.currentUser!!.uid

        override suspend fun saveGroupName(groupName: String): Boolean {
            groupNameContainer.save(groupName)
            return true
        }

        override suspend fun createGroup(filmId: String, photoUrl: String): Boolean {
            val ref = firebaseDatabaseProvider.provideDatabase().child("groups").push()
            val result = ref.setValue(GroupInitial(uid, groupNameContainer.read(), photoUrl, filmId))
            return handle(result)
        }

        override suspend fun fetchGroups(): List<Pair<String, String>> {
            val groups = firebaseDatabaseProvider.provideDatabase()
                .child("groups")
                .orderByChild("userId")
                .equalTo(uid)
            return handleGroups(groups)
        }

        private suspend fun handle(data: Task<Void>) = suspendCoroutine<Boolean> { cont ->
            data.addOnSuccessListener { cont.resume(true) }
                .addOnFailureListener { cont.resume(false) }
        }

        private suspend fun handleGroups(query: Query) =
            suspendCoroutine<List<Pair<String, String>>> { cont ->
                query.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val data =
                            snapshot.children.mapNotNull { it.getValue(GroupInitial::class.java) }
                        cont.resume(data.map { Pair(it.name, it.photoUrl) })
                    }

                    override fun onCancelled(error: DatabaseError) = Unit
                })
            }
    }
}