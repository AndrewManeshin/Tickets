package com.darkular.tickets.sl

import com.darkular.tickets.data.chat.UserId
import com.darkular.tickets.data.chats.ChatDataMapper
import com.darkular.tickets.data.chats.ChatsRepository
import com.darkular.tickets.data.chats.UserChatDataMapper
import com.darkular.tickets.domain.chats.ChatDomainMapper
import com.darkular.tickets.domain.chats.ChatsInteractor
import com.darkular.tickets.domain.chats.UserChatDomainMapper
import com.darkular.tickets.sl.core.BaseModule
import com.darkular.tickets.sl.core.CoreModule
import com.darkular.tickets.ui.chats.ChatsCommunication
import com.darkular.tickets.ui.chats.ChatsViewModel


class ChatsModule(
    private val coreModule: CoreModule
) : BaseModule<ChatsViewModel> {
    override fun viewModel() = ChatsViewModel(
        ChatsCommunication.Base(),
        coreModule.navigationCommunication(),
        ChatsInteractor.Base(
            ChatsRepository.Base(
                coreModule.firebaseDatabaseProvider(),
                UserId(coreModule.provideSharedPreferences())
            ),
            ChatDataMapper.Base(),
            UserChatDataMapper.Base()
        ),
        ChatDomainMapper.Base(),
        UserChatDomainMapper.Base()
    )
}