package com.darkular.tickets.domain.chat


interface MessagesDomain {

    fun <T> map(mapper: MessagesDomainToUiMapper<T>): T

    data class Success(private val messages: List<MessageDomain>) : MessagesDomain{
        override fun <T> map(mapper: MessagesDomainToUiMapper<T>): T = mapper.map(messages)
    }

    data class Fail(private val error: String) : MessagesDomain {
        override fun <T> map(mapper: MessagesDomainToUiMapper<T>): T = mapper.map(error)
    }
}