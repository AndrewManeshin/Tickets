package com.darkular.tickets.presentation.main

import com.darkular.tickets.presentation.core.Communication

interface NavigationCommunication : Communication<NavigationUi> {
    class Base : Communication.Base<NavigationUi>(), NavigationCommunication
}