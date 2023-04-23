package com.darkular.tickets.ui.search

import com.darkular.tickets.core.Abstract
import com.darkular.tickets.ui.core.AbstractView

interface SearchResultListUi : Abstract.UiObject,
    Abstract.Object<Unit, Abstract.Mapper.Data<List<SearchResultUi>, Unit>> {

    class Base(private val list: List<SearchResultUi>) : SearchResultListUi {
        override fun map(mapper: Abstract.Mapper.Data<List<SearchResultUi>, Unit>) =
            mapper.map(list)
    }
}

interface SearchResultUi {

    fun chat(chat: Chat) = Unit
    fun map(avatar: AbstractView.Image, userName: AbstractView.Text) = Unit

    class User(
        private val id: String,
        private val name: String,
        private val photoUrl: String,
    ) : SearchResultUi {
        override fun chat(chat: Chat) = chat.startChatWith(id)
        override fun map(avatar: AbstractView.Image, userName: AbstractView.Text) {
            avatar.load(photoUrl)
            userName.map(name)
        }
    }

    class Group(
        private val id: String,
        private val name: String,
    ) : SearchResultUi {
        override fun chat(chat: Chat) = chat.startGroupChat(id)
        override fun map(avatar: AbstractView.Image, userName: AbstractView.Text) =
            userName.map(name)
    }

    class Search : SearchResultUi
    class Empty : SearchResultUi
    class NoResults : SearchResultUi
}

interface Chat {

    fun startChatWith(userId: String)
    fun startGroupChat(groupId: String)
}