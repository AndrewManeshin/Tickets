package com.darkular.tickets.core

interface Match<T> {

    fun matches(data:T) : Boolean
}