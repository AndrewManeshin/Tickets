package com.darkular.tickets.presentation.group.create.films

interface MessageMapper {
    fun mapMessage(message: String): Unit =
        throw java.lang.IllegalStateException("cannot be used it type is not Empty and Fail")
}

interface BaseMapper {
    fun mapBase(
        name: String,
        rating: String,
        year: String,
        posterUrlPreview: String
    ): Unit = throw java.lang.IllegalStateException("cannot be used it type is not Base")
}

interface FilmUiMapper : BaseMapper, MessageMapper