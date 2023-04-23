package com.darkular.tickets.ui.chats

import com.darkular.tickets.ui.core.Communication


interface ChatsCommunication : Communication<List<ChatUi>> {
    class Base : Communication.Base<List<ChatUi>>(), ChatsCommunication
}