package com.darkular.tickets.ui.login

import com.darkular.tickets.ui.core.Communication

interface LoginCommunication : Communication<LoginUi> {

    class Base : Communication.Base<LoginUi>(), LoginCommunication
}