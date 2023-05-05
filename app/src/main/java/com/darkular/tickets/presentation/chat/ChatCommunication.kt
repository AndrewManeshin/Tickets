package com.darkular.tickets.presentation.chat

import com.darkular.tickets.presentation.core.Communication


interface ChatCommunication : Communication<List<MessageUi>> {
    class Base : Communication.Base<List<MessageUi>>(), ChatCommunication
}