package com.darkular.tickets.domain.chats

import com.darkular.tickets.data.chats.*
import com.darkular.tickets.data.group.groups.GroupsDataRealtimeUpdateCallback
import com.darkular.tickets.data.group.groups.GroupsRepository
import com.darkular.tickets.presentation.chats.ChatsRealtimeUpdateCallback

interface ChatsInteractor {
    fun stopGettingUpdates()
    fun startGettingUpdates(callback: ChatsRealtimeUpdateCallback)
    suspend fun userInfo(userId: String): ChatInfoDomain
    suspend fun groupInfo(groupId: String): ChatInfoDomain

    fun saveChat(data: String)
    fun saveGroup(data: String)

    class Base(
        private val chatsRepository: ChatsRepository,
        private val groupsRepository: GroupsRepository,
        private val mapper: ChatDataMapper<ChatDomain>,
        private val userChatMapper: UserChatDataMapper<ChatInfoDomain>
    ) : ChatsInteractor {

        private var callback: ChatsRealtimeUpdateCallback = ChatsRealtimeUpdateCallback.Empty

        private val chatsDataCallback = object : ChatsDataRealtimeUpdateCallback {
            override fun updateChats(chatDataList: List<ChatData>) {
                callback.updateChats(chatDataList.map { it.map(mapper) })
            }
        }

        private val groupsDataCallBack = object : GroupsDataRealtimeUpdateCallback {
            override fun updateGroups(groupDataList: List<ChatData>) {
                callback.updateGroups(groupDataList.map { it.map(mapper) })
            }
        }

        override fun stopGettingUpdates() {
            callback = ChatsRealtimeUpdateCallback.Empty
        }

        override fun startGettingUpdates(callback: ChatsRealtimeUpdateCallback) {
            this.callback = callback
            chatsRepository.startGettingUpdates(chatsDataCallback)
            groupsRepository.startGettingUpdates(groupsDataCallBack)
        }

        override suspend fun userInfo(userId: String) =
            chatsRepository.userInfo(userId).map(userChatMapper)

        override suspend fun groupInfo(groupId: String) =
            groupsRepository.groupInfo(groupId).map(userChatMapper)

        override fun saveChat(data: String) = chatsRepository.save(data)
        override fun saveGroup(data: String) = groupsRepository.save(data)
    }
}