package com.darkular.tickets.domain.chats

import com.darkular.tickets.ui.chats.UserChatUi


interface UserChatDomain {

    fun <T> map(mapper: UserChatDomainMapper<T>): T

    class Base(
        private val id: String,
        private val name: String,
        private val avatarUrl: String
    ) : UserChatDomain {
        override fun <T> map(mapper: UserChatDomainMapper<T>) = mapper.map(id, name, avatarUrl)
    }
}

interface UserChatDomainMapper<T> {
    fun map(id: String, name: String, avatarUrl: String): T

    class Base : UserChatDomainMapper<UserChatUi> {
        override fun map(id: String, name: String, avatarUrl: String) =
            UserChatUi.Base(id, name, avatarUrl)
    }
}