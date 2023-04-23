package com.darkular.tickets.ui.groups.create

import com.darkular.tickets.ui.core.Communication


interface CreateGroupCommunication : Communication<CreateGroupUi> {
    class Base : Communication.Base<CreateGroupUi>(), CreateGroupCommunication
}