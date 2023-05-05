package com.darkular.tickets.presentation.main

import com.darkular.tickets.R
import com.darkular.tickets.core.Abstract

interface NavigationUi : Abstract.UiObject {

    fun data(): Int//todo make better
    fun isBaseLevel(): Boolean

    class BaseLevel(private val id: Int = R.id.navigation_profile) : NavigationUi {
        override fun isBaseLevel() = true
        override fun data() = id
    }

    class SecondLevel(private val id: Int) : NavigationUi {
        override fun isBaseLevel() = false
        override fun data() = id
    }
}