package com.darkular.tickets.sl.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.darkular.tickets.presentation.chat.ChatViewModel
import com.darkular.tickets.presentation.chats.ChatsViewModel
import com.darkular.tickets.presentation.group.chat.GroupViewModel
import com.darkular.tickets.presentation.group.create.CreateGroupViewModel
import com.darkular.tickets.presentation.group.create.films.FilmsViewModel
import com.darkular.tickets.presentation.login.LoginViewModel
import com.darkular.tickets.presentation.main.MainViewModel
import com.darkular.tickets.presentation.profile.MyProfileViewModel
import com.darkular.tickets.presentation.search.SearchViewModel

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
        put(GroupViewModel::class.java, Feature.GROUP)
        put(FilmsViewModel::class.java, Feature.FILMS)
    }
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val feature =
            map[modelClass] ?: throw IllegalStateException("unknown viewModel $modelClass")
        return dependencyContainer.module(feature).viewModel() as T
    }
}