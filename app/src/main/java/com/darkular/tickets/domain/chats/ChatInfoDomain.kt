package com.darkular.tickets.domain.chats

import com.darkular.tickets.presentation.chats.ChatInfoUi

interface ChatInfoDomain {

    fun <T> map(mapper: ChatDomainInfoMapper<T>): T

    class User(
        private val id: String,
        private val name: String,
        private val avatarUrl: String
    ) : ChatInfoDomain {
        override fun <T> map(mapper: ChatDomainInfoMapper<T>) = mapper.mapToUserInfo(id, name, avatarUrl)
    }

    class Group(
        private val id: String,
        private val name: String,
        private val avatarUrl: String
    ) : ChatInfoDomain {
        override fun <T> map(mapper: ChatDomainInfoMapper<T>) = mapper.mapToGroupInfo(id, name, avatarUrl)
    }
}

interface ChatDomainInfoMapper<T> {
    fun mapToUserInfo(id: String, name: String, avatarUrl: String): T
    fun mapToGroupInfo(id: String, name: String, avatarUrl: String): T

    class Base : ChatDomainInfoMapper<ChatInfoUi> {
        override fun mapToUserInfo(id: String, name: String, avatarUrl: String) =
            ChatInfoUi.ChatUser(id, name, avatarUrl)

        override fun mapToGroupInfo(id: String, name: String, avatarUrl: String) =
            ChatInfoUi.Group(id, name, avatarUrl)
    }
}