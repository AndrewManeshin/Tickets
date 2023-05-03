package com.darkular.tickets.sl

import com.darkular.tickets.data.chat.BaseMessagesDataMapper
import com.darkular.tickets.data.chat.ChatRepository
import com.darkular.tickets.data.chat.InternetConnection
import com.darkular.tickets.data.chat.UserId
import com.darkular.tickets.domain.chat.ChatInteractor
import com.darkular.tickets.sl.core.BaseModule
import com.darkular.tickets.sl.core.CoreModule
import com.darkular.tickets.ui.chat.BaseMessagesDomainToUiMapper
import com.darkular.tickets.ui.chat.ChatCommunication
import com.darkular.tickets.ui.chat.ChatViewModel
import com.darkular.tickets.ui.chat.MessageDomainToUiMapper

class ChatModule(private val coreModule: CoreModule) : BaseModule<ChatViewModel> {
    override fun viewModel(): ChatViewModel {
        return ChatViewModel(
            ChatCommunication.Base(),
            ChatInteractor.Base(
                ChatRepository.Base(
                    coreModule.firebaseDatabaseProvider(),
                    InternetConnection.Base(coreModule.provideConnectivityManager()),
                    UserId(coreModule.provideSharedPreferences())
                ),
                BaseMessagesDataMapper()
            ),
            BaseMessagesDomainToUiMapper(MessageDomainToUiMapper.Base())
        )
    }
}