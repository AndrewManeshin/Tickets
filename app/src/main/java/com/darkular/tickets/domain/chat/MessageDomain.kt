package com.darkular.tickets.domain.chat

import android.net.Uri
import com.darkular.tickets.ui.chat.MessageDomainToUiMapper


interface MessageDomain {

    fun <T> map(mapper: MessageDomainToUiMapper<T>): T

    data class MyMessageDomain(private val text: String, private val isRead: Boolean) :
        MessageDomain {
        override fun <T> map(mapper: MessageDomainToUiMapper<T>): T {
            return mapper.map("", isRead, text, true, "")
        }
    }

    data class UserMessageDomain(
        private val id: String,
        private val text: String,
        private val avatarUrl: String,
        private val isRead: Boolean
    ) : MessageDomain {
        override fun <T> map(mapper: MessageDomainToUiMapper<T>): T {
            return mapper.map(id, isRead, text, false, avatarUrl)
        }
    }
}