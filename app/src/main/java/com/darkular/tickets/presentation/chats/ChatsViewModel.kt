package com.darkular.tickets.presentation.chats

import androidx.lifecycle.viewModelScope
import com.darkular.tickets.R
import com.darkular.tickets.domain.chats.ChatDomain
import com.darkular.tickets.domain.chats.ChatDomainMapper
import com.darkular.tickets.domain.chats.ChatsInteractor
import com.darkular.tickets.domain.chats.ChatDomainInfoMapper
import com.darkular.tickets.presentation.core.BaseViewModel
import com.darkular.tickets.presentation.main.NavigationCommunication
import com.darkular.tickets.presentation.main.NavigationUi
import com.darkular.tickets.presentation.search.Chat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatsViewModel(
    communication: ChatsCommunication,
    private val navigation: NavigationCommunication,
    private val interactor: ChatsInteractor,
    private val mapper: ChatDomainMapper<ChatUi>,
    private val userMapper: ChatDomainInfoMapper<ChatInfoUi>,
    private val dispatchersIO: CoroutineDispatcher = Dispatchers.IO,
    private val dispatchersMain: CoroutineDispatcher = Dispatchers.Main,
) : BaseViewModel<ChatsCommunication, List<ChatUi>>(communication), Chat {

    private val chatsMap: MutableMap<String, ChatUi> = mutableMapOf()
    private val groupsMap: MutableMap<String, ChatUi> = mutableMapOf()

    private val chatsRealtimeUpdateCallback = object : ChatsRealtimeUpdateCallback {
        override fun updateChats(chats: List<ChatDomain>) {
            viewModelScope.launch(dispatchersIO) {
                chats.forEach { handleChat(it) }
                val chatList = uiList()
                withContext(dispatchersMain) { communication.map(chatList) }
            }
        }

        override fun updateGroups(groups: List<ChatDomain>) {
            viewModelScope.launch(dispatchersIO) {
                groups.forEach { handleGroup(it) }
                val groupList = uiList()
                withContext(dispatchersMain) { communication.map(groupList) }
            }
        }
    }

    private fun handleChat(chatDomain: ChatDomain) {
        val chatUi: ChatUi = chatDomain.map(mapper)
        if (!chatsMap.containsValue(chatUi)) chatUi.put(chatsMap)
    }

    private fun handleGroup(groupDomain: ChatDomain) {
        val chatUi: ChatUi = groupDomain.map(mapper)
        if (!groupsMap.containsValue(chatUi)) chatUi.put(groupsMap)
    }

    private suspend fun uiList(): List<ChatUi> {
        val chats = chatsMap.map { (userId, chatUi) ->
            val userUi = interactor.userInfo(userId).map(userMapper)
            chatUi.aggregatedWith(userUi)
        }
        val groups = groupsMap.map { (groupId, chatUi) ->
            val groupUi = interactor.groupInfo(groupId).map(userMapper)
            chatUi.aggregatedWith(groupUi)
        }
        return chats.plus(groups).sortedByDescending { it.sort() }
    }

    override fun startChatWith(userId: String) {
        interactor.saveChat(userId)
        navigation.map(NavigationUi.SecondLevel(R.id.chat_screen))
    }

    override fun startGroupChat(groupId: String) {
        interactor.saveGroup(groupId)
        navigation.map(NavigationUi.SecondLevel(R.id.group_chat_screen))
    }

    fun startGettingUpdates() {
        interactor.startGettingUpdates(chatsRealtimeUpdateCallback)
    }

    fun stopGettingUpdates() {
        interactor.stopGettingUpdates()
    }
}

interface ChatsRealtimeUpdateCallback {

    fun updateChats(chats: List<ChatDomain>)

    fun updateGroups(groups: List<ChatDomain>)

    object Empty : ChatsRealtimeUpdateCallback {
        override fun updateChats(chats: List<ChatDomain>) = Unit
        override fun updateGroups(groups: List<ChatDomain>) = Unit
    }
}