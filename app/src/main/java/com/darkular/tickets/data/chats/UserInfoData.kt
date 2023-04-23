package com.darkular.tickets.data.chats

import com.darkular.tickets.domain.chats.UserChatDomain


interface UserInfoData {

    fun <T> map(mapper: UserChatDataMapper<T>): T

    class Base(
        private val id: String,
        private val name: String,
        private val avatarUrl: String
    ) : UserInfoData {
        override fun <T> map(mapper: UserChatDataMapper<T>) = mapper.map(id, name, avatarUrl)
    }
}

interface UserChatDataMapper<T> {

    fun map(id: String, name: String, avatarUrl: String): T

    class Base : UserChatDataMapper<UserChatDomain> {
        override fun map(id: String, name: String, avatarUrl: String) =
            UserChatDomain.Base(id, name, avatarUrl)
    }
}