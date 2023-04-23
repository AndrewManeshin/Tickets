package com.darkular.tickets.core

interface TextMapper<T> : Abstract.Mapper.Data<String, T> {
    interface Void : TextMapper<Unit> {
        override fun map(data: String) = Unit
    }
}