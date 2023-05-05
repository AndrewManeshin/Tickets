package com.darkular.tickets.presentation.group.create

import com.darkular.tickets.presentation.core.Communication


interface CreateGroupCommunication : Communication<CreateGroupUi> {
    class Base : Communication.Base<CreateGroupUi>(), CreateGroupCommunication
}