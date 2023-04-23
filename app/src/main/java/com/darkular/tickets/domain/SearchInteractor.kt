package com.darkular.tickets.domain

import com.darkular.tickets.data.search.SearchData
import com.darkular.tickets.data.search.SearchGroupRepository
import com.darkular.tickets.data.search.SearchUserRepository


interface SearchInteractor {

    suspend fun search(query: String): List<SearchData>
    suspend fun initChat(groupId: String): Boolean
    suspend fun initChatWith(userId: String): Boolean

    class Base(
        private val searchUserRepository: SearchUserRepository,
        private val searchGroupRepository: SearchGroupRepository
    ) : SearchInteractor {

        override suspend fun search(query: String): List<SearchData> {
            val data = ArrayList(searchGroupRepository.search(query))
            data.addAll(searchUserRepository.search(query))
            return data
        }

        override suspend fun initChat(groupId: String) =
            searchGroupRepository.initChat(groupId)

        override suspend fun initChatWith(userId: String) =
            searchUserRepository.initChatWith(userId)
    }
}