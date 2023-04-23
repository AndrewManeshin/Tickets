package com.darkular.tickets.domain.chat

import com.darkular.tickets.data.chat.ChatRepository
import com.darkular.tickets.data.chat.MessagesData
import com.darkular.tickets.data.chat.MessagesDataMapper
import com.darkular.tickets.ui.chat.MessagesRealtimeUpdateCallback
import com.darkular.tickets.ui.chat.ReadMessage


interface ChatInteractor : ReadMessage {

    suspend fun send(message: String): Boolean

    fun stopGettingUpdates()
    fun startGettingUpdates(callback: MessagesRealtimeUpdateCallback)

    class Base(
        private val repository: ChatRepository,
        private val mapper: MessagesDataMapper<MessagesDomain>
    ) : ChatInteractor {

        private var callback: MessagesRealtimeUpdateCallback = MessagesRealtimeUpdateCallback.Empty

        private val dataCallback = object : MessagesDataRealtimeUpdateCallback {
            override fun updateMessages(messagesData: MessagesData) {
                callback.updateMessages(messagesData.map(mapper))
            }
        }

        override suspend fun send(message: String) = try {
            repository.sendMessage(message)
        } catch (e: Exception) {
            false
        }

        override fun stopGettingUpdates() {
            callback = MessagesRealtimeUpdateCallback.Empty
            repository.stopGettingUpdates()
        }

        override fun startGettingUpdates(callback: MessagesRealtimeUpdateCallback) {
            this.callback = callback
            repository.startGettingUpdates(dataCallback)
        }

        override fun readMessage(id: String) = repository.readMessage(id)
    }
}

interface MessagesDataRealtimeUpdateCallback {
    fun updateMessages(messagesData: MessagesData)

    object Empty : MessagesDataRealtimeUpdateCallback {
        override fun updateMessages(messagesData: MessagesData) = Unit
    }
}