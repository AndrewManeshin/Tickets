package com.darkular.tickets.presentation.main

import com.darkular.tickets.R
import com.darkular.tickets.presentation.chat.ChatFragment
import com.darkular.tickets.presentation.chats.ChatsFragment
import com.darkular.tickets.presentation.core.BaseViewModel
import com.darkular.tickets.presentation.group.chat.GroupFragment
import com.darkular.tickets.presentation.group.create.CreateGroupFragment
import com.darkular.tickets.presentation.profile.MyProfileFragment
import com.darkular.tickets.presentation.search.SearchFragment

class MainViewModel(
    communication: NavigationCommunication,
    private val screenContainer: ScreenContainer
) : BaseViewModel<NavigationCommunication, NavigationUi>(communication) {

    fun changeScreen(menuItemId: Int) {
        screenContainer.save(menuItemId)
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
        changeScreen(screenContainer.read())
    }
}