package com.darkular.tickets.sl.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.darkular.tickets.ui.chat.ChatViewModel
import com.darkular.tickets.ui.chats.ChatsViewModel
import com.darkular.tickets.ui.groups.create.CreateGroupViewModel
import com.darkular.tickets.ui.login.LoginViewModel
import com.darkular.tickets.ui.main.MainViewModel
import com.darkular.tickets.ui.profile.MyProfileViewModel
import com.darkular.tickets.ui.search.SearchViewModel

class ViewModelsFactory(
    private val dependencyContainer: DependencyContainer,
) : ViewModelProvider.Factory {

    private val map = HashMap<Class<*>, Feature>().apply {
        put(LoginViewModel::class.java, Feature.LOGIN)
        put(MainViewModel::class.java, Feature.MAIN)
        put(SearchViewModel::class.java, Feature.SEARCH)
        put(MyProfileViewModel::class.java, Feature.MY_PROFILE)
        put(ChatsViewModel::class.java, Feature.CHATS)
        put(ChatViewModel::class.java, Feature.CHAT)
        put(CreateGroupViewModel::class.java, Feature.CREATE_GROUP)
    }
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val feature =
            map[modelClass] ?: throw IllegalStateException("unknown viewModel $modelClass")
        return dependencyContainer.module(feature).viewModel() as T
    }
}