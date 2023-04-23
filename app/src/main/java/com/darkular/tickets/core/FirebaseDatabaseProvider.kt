package com.darkular.tickets.core

import com.darkular.tickets.BuildConfig
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

interface FirebaseDatabaseProvider {

    fun provideDatabase(): DatabaseReference

    class Base : FirebaseDatabaseProvider {

        init {
            Firebase.database(BuildConfig.DATABASE_URL).setPersistenceEnabled(false)

            provideDatabase().run {
                addValueEventListener(EmptyDataListener())
                child("users")
                    .addValueEventListener(EmptyDataListener())
            }
        }

        override fun provideDatabase(): DatabaseReference {
            return Firebase.database(BuildConfig.DATABASE_URL).reference.root
        }

        private inner class EmptyDataListener : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) = Unit
            override fun onCancelled(error: DatabaseError) = Unit
        }
    }
}