package com.darkular.tickets.ui.chat

import com.darkular.tickets.ui.core.Communication


interface ChatCommunication : Communication<List<MessageUi>> {
    class Base : Communication.Base<List<MessageUi>>(), ChatCommunication
}