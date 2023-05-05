package com.darkular.tickets.presentation.login

import com.darkular.tickets.presentation.core.Communication

interface LoginCommunication : Communication<LoginUi> {

    class Base : Communication.Base<LoginUi>(), LoginCommunication
}