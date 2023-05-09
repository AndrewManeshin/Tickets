package com.darkular.tickets.core

interface Comparable<T> {

    fun same(data: T): Boolean
    fun sameContent(data: T): Boolean
}