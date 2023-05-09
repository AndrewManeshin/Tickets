package com.darkular.tickets.presentation.group.create.films

class FilmsUi(private val films: List<FilmUi>) {

    fun map(communications: FilmsCommunication) = communications.map(films)
}