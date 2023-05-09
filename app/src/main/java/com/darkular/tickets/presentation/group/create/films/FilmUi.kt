package com.darkular.tickets.presentation.group.create.films

import com.darkular.tickets.core.Comparable

interface FilmUi : Comparable<FilmUi> {

    interface OnSelected {
        fun OnSelected(filmId: String, posterUrl: String)
    }

    fun map(ui: FilmUiMapper)
    fun click(listener: OnSelected) = Unit
    fun type(): Type

    class Empty() : FilmUi {

        override fun map(ui: FilmUiMapper) = Unit
        override fun type() = Type.EMPTY

        override fun same(data: FilmUi) = data.type() == Type.EMPTY
        override fun sameContent(data: FilmUi) = data.same(data)
    }

    object Progress : FilmUi {
        override fun map(ui: FilmUiMapper) = Unit
        override fun type() = Type.PROGRESS

        override fun same(data: FilmUi) = data.type() == Type.PROGRESS
        override fun sameContent(data: FilmUi) = same(data)
    }

    class Fail(private val message: String) : FilmUi {
        override fun map(ui: FilmUiMapper) = ui.mapMessage(message)
        override fun type() = Type.FAIL

        override fun same(data: FilmUi) =
            data.type() == Type.FAIL && (data as Fail).message == message

        override fun sameContent(data: FilmUi) = data.type() == Type.FAIL
    }

    class Base(
        private val id: String,
        private val name: String,
        private val posterUrl: String,
        private val rating: String,
        private val year: String
    ) : FilmUi {

        override fun click(listener: OnSelected) {
            listener.OnSelected(id, posterUrl)
        }

        override fun map(ui: FilmUiMapper) = ui.mapBase(name, rating, year, posterUrl)
        override fun type() = Type.BASE

        override fun same(data: FilmUi) = data.type() == Type.BASE && (data as Base).id == id
        override fun sameContent(data: FilmUi) =
            data.type() == Type.BASE && (data as Base).name == name && data.posterUrl == posterUrl
                    && data.rating == rating && data.year == year
    }

    enum class Type {
        EMPTY,
        PROGRESS,
        BASE,
        FAIL
    }
}