package com.darkular.tickets.presentation.chat

import androidx.lifecycle.viewModelScope
import com.darkular.tickets.domain.chat.ChatInteractor
import com.darkular.tickets.domain.chat.MessagesDomain
import com.darkular.tickets.domain.chat.MessagesDomainToUiMapper
import com.darkular.tickets.presentation.core.BaseViewModel
import kotlinx.coroutines.*

class ChatViewModel(
    communication: ChatCommunication,
    private val interactor: ChatInteractor,
    private val mapper: MessagesDomainToUiMapper<List<MessageUi>>,
    private val dispatchersIO: CoroutineDispatcher = Dispatchers.IO,
    private val dispatchersMain: CoroutineDispatcher = Dispatchers.Main,
) : BaseViewModel<ChatCommunication, List<MessageUi>>(communication), TextMapper.Void, ReadMessage {

    private val myMessagesWaitList = ArrayList<MessageUi>()
    private var incomeMessages: List<MessageUi> = ArrayList()

    /**
     * try to send one more time
     */
    override fun map(data: String) {
        myMessagesWaitList.remove(MessageUi.Mine(data, MyMessageUiState.FAILED))
        send(data)
    }

    fun send(message: String) {
        val element = MessageUi.Mine(message, MyMessageUiState.PROGRESS)
        myMessagesWaitList.add(element)
        showAllMessages()

        viewModelScope.launch(dispatchersIO) {
            val success = interactor.send(message)
            withContext(dispatchersMain) {
                if (!success)  {
                    val index = myMessagesWaitList.indexOf(element)
                    if (index != -1)
                        myMessagesWaitList[index] = element.newState(MyMessageUiState.FAILED)
                    showAllMessages()
                }
            }
        }
    }

    private val messagesRealtimeUpdateCallback = object : MessagesRealtimeUpdateCallback {
        override fun updateMessages(messagesDomain: MessagesDomain) {
            val messages = messagesDomain.map(mapper)
            if (myMessagesWaitList.isNotEmpty()) {
                val newMessagesWaitList = myMessagesWaitList.filter { message ->
                    messages.find { it.same(message) } == null
                }
                myMessagesWaitList.clear()
                myMessagesWaitList.addAll(newMessagesWaitList)
            }
            incomeMessages = messages
            viewModelScope.launch(dispatchersMain) { showAllMessages() }
        }
    }

    private fun showAllMessages() = communication.map(
        if (myMessagesWaitList.isEmpty()) incomeMessages
        else ArrayList(incomeMessages).apply { addAll(myMessagesWaitList) }
    )

    fun startGettingUpdates() {
        interactor.startGettingUpdates(messagesRealtimeUpdateCallback)
    }

    fun stopGettingUpdates() {
        interactor.stopGettingUpdates()
    }

    override fun readMessage(id: String) {
        viewModelScope.launch(dispatchersIO) { interactor.readMessage(id) }
    }
}

interface MessagesRealtimeUpdateCallback {
    fun updateMessages(messagesDomain: MessagesDomain)

    object Empty : MessagesRealtimeUpdateCallback {
        override fun updateMessages(messagesDomain: MessagesDomain) = Unit
    }
}