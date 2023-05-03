package com.darkular.tickets.ui.search

import androidx.lifecycle.viewModelScope
import com.darkular.tickets.R
import com.darkular.tickets.core.Delay
import com.darkular.tickets.domain.search.SearchInteractor
import com.darkular.tickets.ui.core.BaseViewModel
import com.darkular.tickets.ui.main.NavigationCommunication
import com.darkular.tickets.ui.main.NavigationUi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(
    searchCommunication: SearchCommunication,
    private val mapper: SearchResultsMapper,
    private val interactor: SearchInteractor,
    private val navigation: NavigationCommunication,
    private val dispatchersIO: CoroutineDispatcher = Dispatchers.IO,
    private val dispatchersMain: CoroutineDispatcher = Dispatchers.Main,
) : BaseViewModel<SearchCommunication, SearchResultListUi>(searchCommunication), Search, Chat {

    private val delay = Delay<String> { query ->
        viewModelScope.launch(dispatchersIO) { find(query) }
    }

    private val initial = SearchResultListUi.Base(listOf(SearchResultUi.Search()))

    private var cleared = false

    override fun search(query: String) {
        cleared = query.length < 3
        if (cleared) {
            delay.clear()
            communication.map(initial)
        } else {
            communication.map(SearchResultListUi.Base(listOf(SearchResultUi.Empty())))
            delay.add(query.lowercase())
        }
    }

    override fun startChatWith(userId: String) {
        viewModelScope.launch(dispatchersIO) {
            if (interactor.initChatWith(userId))
                withContext(dispatchersMain) { navigation.map(NavigationUi.SecondLevel(R.id.chat_screen)) }
            //todo else handle error
        }
    }

    override fun startGroupChat(groupId: String) {
        viewModelScope.launch(dispatchersIO) {
            if (interactor.initChat(groupId))
                withContext(dispatchersMain) { navigation.map(NavigationUi.SecondLevel(R.id.group_chat_screen)) }
            //todo else handle error
        }
    }

    private suspend fun find(query: String) {
        val raw = interactor.search(query)
        val list = raw.map { it.map(mapper) }
        val result =
            if (list.isEmpty()) listOf<SearchResultUi>(SearchResultUi.NoResults())
            else list
        if (!cleared)
            withContext(dispatchersMain) { communication.map(SearchResultListUi.Base(result)) }
    }

    fun init() {
        communication.map(initial)
    }
}