package com.darkular.tickets.data.chats

import com.darkular.tickets.domain.chats.ChatInfoDomain

interface ChatInfoData {

    fun <T> map(mapper: UserChatDataMapper<T>): T

    class User(
        private val otherUserId: String,
        private val name: String,
        private val avatarUrl: String
    ) : ChatInfoData {
        override fun <T> map(mapper: UserChatDataMapper<T>) = mapper.mapToUserInfo(otherUserId, name, avatarUrl)
    }

    class Group(
        private val id: String,
        private val name: String,
        private val groupImageUrl: String
    ) : ChatInfoData {
        override fun <T> map(mapper: UserChatDataMapper<T>) = mapper.mapToGroupInfo(id, name, groupImageUrl)
    }
}

interface UserChatDataMapper<T> {

    fun mapToUserInfo(otherUserId: String, name: String, avatarUrl: String): T
    fun mapToGroupInfo(id: String, name: String, groupImageUrl: String): T

    class Base : UserChatDataMapper<ChatInfoDomain> {
        override fun mapToUserInfo(otherUserId: String, name: String, avatarUrl: String) =
            ChatInfoDomain.User(otherUserId, name, avatarUrl)

        override fun mapToGroupInfo(id: String, name: String, groupImageUrl: String) =
            ChatInfoDomain.Group(id, name, groupImageUrl)
    }
}