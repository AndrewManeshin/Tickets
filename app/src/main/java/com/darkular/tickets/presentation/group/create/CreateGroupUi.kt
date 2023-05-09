package com.darkular.tickets.presentation.group.create

import com.darkular.tickets.core.Abstract


interface CreateGroupUi : Abstract.UiObject,
    Abstract.Object<Unit, Abstract.Mapper.Data<List<Pair<String, String>>, Unit>> {

    class Base(private val groups: List<Pair<String, String>>) : CreateGroupUi {
        override fun map(mapper: Abstract.Mapper.Data<List<Pair<String, String>>, Unit>) {
            mapper.map(groups)
        }
    }
}