package com.darkular.tickets.domain.group

import com.darkular.tickets.data.chat.MessagesData
import com.darkular.tickets.data.chat.MessagesDataMapper
import com.darkular.tickets.data.group.group.GroupRepository
import com.darkular.tickets.domain.chat.MessagesDataRealtimeUpdateCallback
import com.darkular.tickets.domain.chat.MessagesDomain
import com.darkular.tickets.presentation.chat.MessagesRealtimeUpdateCallback
import com.darkular.tickets.presentation.chat.ReadMessage

interface GroupInteractor : ReadMessage {

    suspend fun send(message: String): Boolean

    fun stopGettingUpdates()
    fun startGettingUpdates(callback: MessagesRealtimeUpdateCallback)

    class Base(
        private val repository: GroupRepository,
        private val mapper: MessagesDataMapper<MessagesDomain>,
    ) : GroupInteractor {

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