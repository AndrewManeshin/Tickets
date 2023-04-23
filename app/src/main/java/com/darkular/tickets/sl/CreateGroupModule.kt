package com.darkular.tickets.sl

import com.darkular.tickets.data.groups.create.CreateGroupRepository
import com.darkular.tickets.sl.core.BaseModule
import com.darkular.tickets.sl.core.CoreModule
import com.darkular.tickets.ui.groups.create.CreateGroupCommunication
import com.darkular.tickets.ui.groups.create.CreateGroupViewModel


class CreateGroupModule(private val coreModule: CoreModule) : BaseModule<CreateGroupViewModel> {
    override fun viewModel() = CreateGroupViewModel(
        CreateGroupCommunication.Base(),
        CreateGroupRepository.Base(coreModule.firebaseDatabaseProvider())
    )
}