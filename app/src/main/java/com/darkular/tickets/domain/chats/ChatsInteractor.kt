package com.darkular.tickets.domain.chats

import com.darkular.tickets.core.Save
import com.darkular.tickets.data.chats.*
import com.darkular.tickets.ui.chats.ChatsRealtimeUpdateCallback

interface ChatsInteractor : Save<String> {
    fun stopGettingUpdates()
    fun startGettingUpdates(callback: ChatsRealtimeUpdateCallback)
    suspend fun userInfo(userId: String): UserChatDomain

    class Base(
        private val repository: ChatsRepository,
        private val mapper: ChatDataMapper<ChatDomain>,
        private val userChatMapper: UserChatDataMapper<UserChatDomain>
    ) : ChatsInteractor {

        private var callback: ChatsRealtimeUpdateCallback = ChatsRealtimeUpdateCallback.Empty

        private val dataCallback = object : ChatsDataRealtimeUpdateCallback {
            override fun updateChats(chatDataList: List<ChatData>) {
                callback.updateChats(chatDataList.map { it.map(mapper) })
            }
        }

        override fun stopGettingUpdates() {
            callback = ChatsRealtimeUpdateCallback.Empty
        }

        override fun startGettingUpdates(callback: ChatsRealtimeUpdateCallback) {
            this.callback = callback
            repository.startGettingUpdates(dataCallback)
        }

        override suspend fun userInfo(userId: String) =
            repository.userInfo(userId).map(userChatMapper)

        override fun save(data: String) = repository.save(data)
    }
}