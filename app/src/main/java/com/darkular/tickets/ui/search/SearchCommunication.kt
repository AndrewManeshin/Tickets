package com.darkular.tickets.ui.search

import com.darkular.tickets.ui.core.Communication

interface SearchCommunication : Communication<SearchResultListUi> {
    class Base : Communication.Base<SearchResultListUi>(), SearchCommunication
}