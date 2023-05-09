package com.darkular.tickets.presentation.chat

import com.darkular.tickets.core.Abstract
import com.darkular.tickets.core.Comparable
import com.darkular.tickets.presentation.core.AbstractView
import com.darkular.tickets.presentation.core.ClickListener

interface MessageUi : Comparable<MessageUi> {

    fun isMyMessage(): Boolean

    fun map(textMapper: TextMapper.Void, imageView: AbstractView.Image) = Unit
    fun map(textMapper: TextMapper.Void) = Unit
    fun map(textView: AbstractView.Text, progressView: AbstractView, stateView: MessageState) = Unit

    fun newState(state: MyMessageUiState): MessageUi
    fun click(clickListener: ClickListener<MessageUi>)

    fun read(readMessage: ReadMessage)

    data class Mine(
        private val text: String,
        private val state: MyMessageUiState
    ) : MessageUi {
        override fun isMyMessage() = true
        override fun map(textMapper: TextMapper.Void) {
            textMapper.map(text)
        }

        override fun map(
            textView: AbstractView.Text,
            progressView: AbstractView,
            stateView: MessageState
        ) {
            textView.map(text)
            if (state == MyMessageUiState.PROGRESS)
                progressView.show()
            else
                progressView.hide()
            stateView.show(state)
        }

        override fun newState(state: MyMessageUiState) = Mine(text, state)

        override fun same(data: MessageUi) = data is Mine && text == data.text
        override fun sameContent(data: MessageUi) = data is Mine && text == data.text && state == data.state

        override fun click(clickListener: ClickListener<MessageUi>) {
            if (state == MyMessageUiState.FAILED)
                clickListener.click(this)
        }

        override fun read(readMessage: ReadMessage) = Unit
    }

    data class FromUser(
        private val messageId: String,
        private val text: String,
        private val avatarUrl: String,
        private val isRead: Boolean
    ) : MessageUi {
        override fun isMyMessage() = false

        override fun map(textMapper: TextMapper.Void, imageView: AbstractView.Image) {
            textMapper.map(text)
            imageView.load(avatarUrl)
        }

        override fun newState(state: MyMessageUiState) = this

        override fun same(data: MessageUi) = data is FromUser && messageId == data.messageId
        override fun sameContent(data: MessageUi) = data is FromUser && text == data.text && avatarUrl == data.avatarUrl && isRead == data.isRead

        override fun click(clickListener: ClickListener<MessageUi>) = Unit

        override fun read(readMessage: ReadMessage) {
            if (!isRead) readMessage.readMessage(messageId)
        }
    }
}

enum class MyMessageUiState {
    PROGRESS,
    FAILED,
    SENT,
    READ
}

interface TextMapper<T> : Abstract.Mapper.Data<String, T> {
    interface Void : TextMapper<Unit>
}

interface ReadMessage {

    fun readMessage(id: String)
}