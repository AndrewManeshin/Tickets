package com.darkular.tickets.data.chat

sealed class MessagesData {

    abstract fun <T> map(mapper: MessagesDataMapper<T>): T

    data class Success(
        private val messages: List<Pair<String, MessageData>>
    ) : MessagesData() {
        override fun <T> map(mapper: MessagesDataMapper<T>): T = mapper.map(messages)
    }

    data class Fail(private val e: Exception) : MessagesData() {
        override fun <T> map(mapper: MessagesDataMapper<T>) = mapper.map(e)
    }
}