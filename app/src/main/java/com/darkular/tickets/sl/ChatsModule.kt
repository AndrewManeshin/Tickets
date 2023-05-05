package com.darkular.tickets.sl

import com.darkular.tickets.data.chat.UserId
import com.darkular.tickets.data.chats.ChatDataMapper
import com.darkular.tickets.data.chats.ChatsRepository
import com.darkular.tickets.data.chats.UserChatDataMapper
import com.darkular.tickets.data.group.groups.GroupsRepository
import com.darkular.tickets.data.search.GroupId
import com.darkular.tickets.domain.chats.ChatDomainMapper
import com.darkular.tickets.domain.chats.ChatsInteractor
import com.darkular.tickets.domain.chats.ChatDomainInfoMapper
import com.darkular.tickets.sl.core.BaseModule
import com.darkular.tickets.sl.core.CoreModule
import com.darkular.tickets.presentation.chats.ChatsCommunication
import com.darkular.tickets.presentation.chats.ChatsViewModel

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
            GroupsRepository.Base(
                coreModule.firebaseDatabaseProvider(),
                GroupId(coreModule.provideSharedPreferences())
            ),
            ChatDataMapper.Base(),
            UserChatDataMapper.Base()
        ),
        ChatDomainMapper.Base(),
        ChatDomainInfoMapper.Base(),
    )
}