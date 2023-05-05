package com.darkular.tickets.presentation.search

import com.darkular.tickets.presentation.core.Communication

interface SearchCommunication : Communication<SearchResultListUi> {
    class Base : Communication.Base<SearchResultListUi>(), SearchCommunication
}