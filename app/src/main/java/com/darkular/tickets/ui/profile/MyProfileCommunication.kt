package com.darkular.tickets.ui.profile

import com.darkular.tickets.ui.core.Communication

interface MyProfileCommunication : Communication<MyProfileUi> {
    class Base : Communication.Base<MyProfileUi>(), MyProfileCommunication
}