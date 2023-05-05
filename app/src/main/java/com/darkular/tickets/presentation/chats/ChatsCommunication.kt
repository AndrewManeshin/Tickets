package com.darkular.tickets.presentation.chats

import com.darkular.tickets.presentation.core.Communication


interface ChatsCommunication : Communication<List<ChatUi>> {
    class Base : Communication.Base<List<ChatUi>>(), ChatsCommunication
}