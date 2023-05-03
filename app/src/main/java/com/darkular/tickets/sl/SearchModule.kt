package com.darkular.tickets.sl

import com.darkular.tickets.data.search.GroupId
import com.darkular.tickets.data.chat.UserId
import com.darkular.tickets.data.search.SearchGroupRepository
import com.darkular.tickets.data.search.SearchUserRepository
import com.darkular.tickets.domain.search.SearchInteractor
import com.darkular.tickets.sl.core.BaseModule
import com.darkular.tickets.sl.core.CoreModule
import com.darkular.tickets.ui.search.SearchCommunication
import com.darkular.tickets.ui.search.SearchResultsMapper
import com.darkular.tickets.ui.search.SearchViewModel

class SearchModule(private val coreModule: CoreModule) : BaseModule<SearchViewModel> {
    override fun viewModel() = SearchViewModel(
        SearchCommunication.Base(),
        SearchResultsMapper.Base(),
        SearchInteractor.Base(
            SearchUserRepository.Base(
                coreModule.firebaseDatabaseProvider(),
                UserId(coreModule.provideSharedPreferences())
            ),
            SearchGroupRepository.Base(
                coreModule.firebaseDatabaseProvider(),
                GroupId(coreModule.provideSharedPreferences())
            )
        ),
        coreModule.navigationCommunication()
    )
}