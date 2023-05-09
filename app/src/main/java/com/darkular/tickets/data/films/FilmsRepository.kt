package com.darkular.tickets.data.films

import com.darkular.tickets.core.FirebaseDatabaseProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface FilmsRepository {

    suspend fun fetchFilms(): List<FilmModel>

    class Base(private val firebaseDatabaseProvider: FirebaseDatabaseProvider) : FilmsRepository {

        override suspend fun fetchFilms(): List<FilmModel> {
            val films = firebaseDatabaseProvider.provideDatabase()
                .child("films")
            return handleFilms(films)
        }

        private suspend fun handleFilms(query: Query) =
            suspendCoroutine<List<FilmModel>> { cont ->
                query.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val data = snapshot.children.mapNotNull { it.getValue(FilmModel::class.java) }
                        cont.resume(data.map { it })
                    }

                    override fun onCancelled(error: DatabaseError) = Unit
                })
            }
    }
}