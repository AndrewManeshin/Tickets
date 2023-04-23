package com.darkular.tickets.ui.chat

import com.darkular.tickets.domain.chat.MessageDomain
import com.darkular.tickets.domain.chat.MessagesDomainToUiMapper


class BaseMessagesDomainToUiMapper(private val mapper: MessageDomainToUiMapper<MessageUi>) :
    MessagesDomainToUiMapper<List<MessageUi>> {

    override fun map(data: List<MessageDomain>) = data.map { it.map(mapper) }

    override fun map(error: String): List<MessageUi> {
        return listOf(MessageUi.Mine(error, MyMessageUiState.FAILED))//todo make better later
    }
}

interface MessageDomainToUiMapper<T> {
    fun map(id: String, isRead: Boolean, text: String, isMyMessage: Boolean): T
    fun map(error: String): T

    class Base : MessageDomainToUiMapper<MessageUi> {
        override fun map(id: String, isRead: Boolean, text: String, isMyMessage: Boolean) =
            if (isMyMessage)
                MessageUi.Mine(text, if (isRead) MyMessageUiState.READ else MyMessageUiState.SENT)
            else
                MessageUi.FromUser(id, text, isRead)

        override fun map(error: String) = MessageUi.Mine(error, MyMessageUiState.FAILED)
    }
}