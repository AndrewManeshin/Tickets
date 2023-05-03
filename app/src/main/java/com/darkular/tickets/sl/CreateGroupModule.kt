package com.darkular.tickets.sl

import com.darkular.tickets.data.chat.BaseMessagesDataMapper
import com.darkular.tickets.data.chat.InternetConnection
import com.darkular.tickets.data.group.create.CreateGroupRepository
import com.darkular.tickets.data.group.group.GroupRepository
import com.darkular.tickets.data.search.GroupId
import com.darkular.tickets.domain.group.GroupInteractor
import com.darkular.tickets.sl.core.BaseModule
import com.darkular.tickets.sl.core.CoreModule
import com.darkular.tickets.ui.chat.BaseMessagesDomainToUiMapper
import com.darkular.tickets.ui.chat.ChatCommunication
import com.darkular.tickets.ui.chat.MessageDomainToUiMapper
import com.darkular.tickets.ui.groups.chat.GroupViewModel
import com.darkular.tickets.ui.groups.create.CreateGroupCommunication
import com.darkular.tickets.ui.groups.create.CreateGroupViewModel


class CreateGroupModule(private val coreModule: CoreModule) : BaseModule<CreateGroupViewModel> {
    override fun viewModel() = CreateGroupViewModel(
        CreateGroupCommunication.Base(),
        CreateGroupRepository.Base(coreModule.firebaseDatabaseProvider())
    )
}

class GroupModule(private val coreModule: CoreModule) : BaseModule<GroupViewModel> {
    override fun viewModel() = GroupViewModel(
        ChatCommunication.Base(),
        GroupInteractor.Base(
            GroupRepository.Base(
                coreModule.firebaseDatabaseProvider(),
                InternetConnection.Base(coreModule.provideConnectivityManager()),
                GroupId(coreModule.provideSharedPreferences())
            ),
            BaseMessagesDataMapper()
        ),
        BaseMessagesDomainToUiMapper(MessageDomainToUiMapper.Base())
    )
}