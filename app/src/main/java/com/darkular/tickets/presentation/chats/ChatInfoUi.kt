package com.darkular.tickets.presentation.chats

import com.darkular.tickets.presentation.core.AbstractView
import com.darkular.tickets.presentation.search.Chat

interface ChatInfoUi {
    fun map(chatName: AbstractView.Text, chatAvatar: AbstractView.Image)
    fun same(userData: ChatInfoUi): Boolean
    fun startChat(chat: Chat)

    data class ChatUser(
        private val id: String,
        private val name: String,
        private val avatarUrl: String
    ) : ChatInfoUi {

        override fun map(
            chatName: AbstractView.Text,
            chatAvatar: AbstractView.Image
        ) {
            chatName.map(name)
            chatAvatar.load(avatarUrl)
        }

        override fun same(userData: ChatInfoUi) = userData is ChatUser && userData.id == id
        override fun startChat(chat: Chat) = chat.startChatWith(id)
    }
    
    data class Group(
        private val id: String,
        private val name: String,
        private val avatarUrl: String
    ) : ChatInfoUi {

        override fun map(chatName: AbstractView.Text, chatAvatar: AbstractView.Image) {
            chatName.map(name)
            chatAvatar.load(avatarUrl)
        }

        override fun same(userData: ChatInfoUi) = userData is Group && userData.id == id
        override fun startChat(chat: Chat) = chat.startGroupChat(id)
    }
}