package com.darkular.tickets.domain.group

import com.darkular.tickets.data.films.FilmsRepository
import com.darkular.tickets.data.group.create.CreateGroupRepository
import com.darkular.tickets.presentation.group.create.films.FilmUi
import com.darkular.tickets.presentation.group.create.films.FilmsUi

interface CreateGroupInteractor {

    suspend fun saveGroupName(name: String): Boolean
    suspend fun createGroup(filmId: String, photoUrl: String): Boolean
    suspend fun fetchGroups(): List<Pair<String, String>>
    suspend fun fetchFilms(): FilmsUi

    class Base(
        private val groupRepository: CreateGroupRepository,
        private val filmsRepository: FilmsRepository
    ) : CreateGroupInteractor {

        override suspend fun saveGroupName(name: String) = groupRepository.saveGroupName(name)

        override suspend fun createGroup(filmId: String, photoUrl: String) =
            groupRepository.createGroup(filmId, photoUrl)

        override suspend fun fetchGroups() = groupRepository.fetchGroups()

        override suspend fun fetchFilms(): FilmsUi {
            val films = filmsRepository.fetchFilms()
            return when {
                films.isEmpty() -> FilmsUi(listOf(FilmUi.Empty()))
                else -> FilmsUi(films.map {
                    FilmUi.Base(it.filmId, it.name, it.posterUrl, it.rating, it.year)
                })
            }
        }
    }
}