package com.darkular.tickets.presentation.search

import com.darkular.tickets.data.search.SearchData
import com.darkular.tickets.data.search.SearchResultType

interface SearchResultsMapper : SearchData.SearchMapper<SearchResultUi> {

    class Base : SearchResultsMapper {

        override fun map(
            id: String,
            name: String,
            photoUrl: String,
            type: SearchResultType
        ) = if (type == SearchResultType.USER)
            SearchResultUi.User(id, name, photoUrl)
        else
            SearchResultUi.Group(id, name)
    }
}