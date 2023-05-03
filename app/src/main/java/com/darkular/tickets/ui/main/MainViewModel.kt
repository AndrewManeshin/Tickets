package com.darkular.tickets.ui.main

import com.darkular.tickets.R
import com.darkular.tickets.ui.chat.ChatFragment
import com.darkular.tickets.ui.chats.ChatsFragment
import com.darkular.tickets.ui.core.BaseViewModel
import com.darkular.tickets.ui.groups.chat.GroupFragment
import com.darkular.tickets.ui.groups.create.CreateGroupFragment
import com.darkular.tickets.ui.profile.MyProfileFragment
import com.darkular.tickets.ui.search.SearchFragment

class MainViewModel(
    communication: NavigationCommunication,
) : BaseViewModel<NavigationCommunication, NavigationUi>(communication) {

    fun changeScreen(menuItemId: Int) {
        //todo save screen id
        communication.map(NavigationUi.BaseLevel(menuItemId))
    }

    private val idMap = mapOf(
        R.id.navigation_profile to MyProfileFragment::class.java,
        R.id.navigation_search to SearchFragment::class.java,
        R.id.navigation_chats to ChatsFragment::class.java,
        R.id.chat_screen to ChatFragment::class.java,
        R.id.create_group_screen to CreateGroupFragment::class.java,
        R.id.group_chat_screen to GroupFragment::class.java
    )//todo move to some class

    fun getFragment(id: Int): BaseFragment<*> {
        val clazz = idMap[id] ?: throw IllegalStateException("unknown id $id")
        return clazz.newInstance()
    }

    fun init() {
        changeScreen(R.id.navigation_profile) //todo get from navigation cache
    }
}