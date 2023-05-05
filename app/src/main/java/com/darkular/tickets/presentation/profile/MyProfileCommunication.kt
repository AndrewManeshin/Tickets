package com.darkular.tickets.presentation.profile

import com.darkular.tickets.presentation.core.Communication

interface MyProfileCommunication : Communication<MyProfileUi> {
    class Base : Communication.Base<MyProfileUi>(), MyProfileCommunication
}