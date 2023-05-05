package com.darkular.tickets.presentation.chats

import com.darkular.tickets.presentation.core.AbstractView

interface ChatUi {
    fun put(chatsMap: MutableMap<String, ChatUi>)
    fun aggregatedWith(userInfo: ChatInfoUi): ChatUi
    fun mapChat(
        chatName: AbstractView.Text,
        chatAvatar: AbstractView.Image,
        message: AbstractView.Text,
        fromMe: AbstractView,
        unreadMessagesCount: AbstractView.Text
    ) = Unit

    fun mapGroup(
        chatName: AbstractView.Text,
        chatAvatar: AbstractView.Image,
        message: AbstractView.Text,
        lastMessageFromUserName: AbstractView.Text,
        unreadMessagesCount: AbstractView.Text
    ) = Unit

    fun same(chatUi: ChatUi): Boolean
    fun startChat(chat: com.darkular.tickets.presentation.search.Chat)
    fun sort(): Int

    data class Raw(
        private val otherUserId: String,
        private val lastMessageBody: String,
        private val notReadMessagesCount: Int,
        private val lastMessageFromUser: Boolean
    ) : ChatUi {

        override fun put(chatsMap: MutableMap<String, ChatUi>) {
            chatsMap[otherUserId] = this
        }

        override fun aggregatedWith(userInfo: ChatInfoUi) = Chat(
            userInfo, lastMessageBody, lastMessageFromUser, notReadMessagesCount
        )

        override fun same(chatUi: ChatUi) = false

        override fun startChat(chat: com.darkular.tickets.presentation.search.Chat) = chat.startChatWith(otherUserId)
        override fun sort() = 0
    }

    data class Group(
        private val groupId: String,
        private val groupData: ChatInfoUi,
        private val notReadMessagesCount: Int,
        private val lastMessageBody: String,
        private val lastMessageFromUser: Boolean,
        private val lastMessageFromUserName: String
    ) : ChatUi {

        override fun mapGroup(
            chatName: AbstractView.Text,
            chatAvatar: AbstractView.Image,
            message: AbstractView.Text,
            lastMessageFromUserName: AbstractView.Text,
            unreadMessagesCount: AbstractView.Text
        ) {
            groupData.map(chatName, chatAvatar)
            message.map(lastMessageBody)
            if (lastMessageFromUser)
                lastMessageFromUserName.map("you: ")
            else
                lastMessageFromUserName.map(this@Group.lastMessageFromUserName)
            if (notReadMessagesCount < 1)
                unreadMessagesCount.hide()
            else {
                unreadMessagesCount.map(notReadMessagesCount.toString())
                unreadMessagesCount.show()
            }
        }

        override fun put(chatsMap: MutableMap<String, ChatUi>) {
            chatsMap[groupId] = this
        }

        override fun aggregatedWith(userInfo: ChatInfoUi) = this
        override fun same(chatUi: ChatUi) = chatUi is Group && chatUi.groupData.same(groupData)
        override fun startChat(chat: com.darkular.tickets.presentation.search.Chat) = groupData.startChat(chat)
        override fun sort() = notReadMessagesCount
        override fun mapChat(
            chatName: AbstractView.Text,
            chatAvatar: AbstractView.Image,
            message: AbstractView.Text,
            fromMe: AbstractView,
            unreadMessagesCount: AbstractView.Text
        ) = throw IllegalStateException("is not for this type")
    }

    data class Chat(
        private val userData: ChatInfoUi,
        private val lastMessage: String,
        private val fromUser: Boolean,
        private val notReadMessagesCount: Int,
    ) : ChatUi {
        override fun put(chatsMap: MutableMap<String, ChatUi>) = Unit
        override fun aggregatedWith(userInfo: ChatInfoUi) = this
        override fun mapChat(
            chatName: AbstractView.Text,
            chatAvatar: AbstractView.Image,
            message: AbstractView.Text,
            fromMe: AbstractView,
            unreadMessagesCount: AbstractView.Text
        ) {
            userData.map(chatName, chatAvatar)
            message.map(lastMessage)
            if (fromUser)
                fromMe.hide()
            else
                fromMe.show()
            if (notReadMessagesCount < 1)
                unreadMessagesCount.hide()
            else {
                unreadMessagesCount.map(notReadMessagesCount.toString())
                unreadMessagesCount.show()
            }
        }

        override fun same(chatUi: ChatUi) = chatUi is Chat && chatUi.userData.same(userData)
        override fun startChat(chat: com.darkular.tickets.presentation.search.Chat) = userData.startChat(chat)
        override fun sort() = notReadMessagesCount

        override fun mapGroup(
            chatName: AbstractView.Text,
            chatAvatar: AbstractView.Image,
            message: AbstractView.Text,
            lastMessageFromUserName: AbstractView.Text,
            unreadMessagesCount: AbstractView.Text
        ) = throw IllegalStateException("is not for this type")
    }
}