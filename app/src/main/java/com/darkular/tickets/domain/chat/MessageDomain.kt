package com.darkular.tickets.domain.chat

import com.darkular.tickets.presentation.chat.MessageDomainToUiMapper

interface MessageDomain {

    fun <T> map(mapper: MessageDomainToUiMapper<T>): T

    data class MyMessageDomain(
        private val messageId: String,
        private val text: String,
        private val isRead: Boolean
    ) :
        MessageDomain {
        override fun <T> map(mapper: MessageDomainToUiMapper<T>): T {
            return mapper.map(messageId, isRead, text, true, "")
        }
    }

    data class UserMessageDomain(
        private val messageId: String,
        private val text: String,
        private val avatarUrl: String,
        private val isRead: Boolean
    ) : MessageDomain {
        override fun <T> map(mapper: MessageDomainToUiMapper<T>): T {
            return mapper.map(messageId, isRead, text, false, avatarUrl)
        }
    }
}