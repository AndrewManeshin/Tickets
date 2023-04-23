package com.darkular.tickets.ui.main

import com.darkular.tickets.ui.core.Communication

interface NavigationCommunication : Communication<NavigationUi> {
    class Base : Communication.Base<NavigationUi>(), NavigationCommunication
}