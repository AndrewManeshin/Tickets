package com.darkular.tickets.presentation.group.create

import androidx.lifecycle.viewModelScope
import com.darkular.tickets.R
import com.darkular.tickets.data.group.create.CreateGroupRepository
import com.darkular.tickets.domain.group.CreateGroupInteractor
import com.darkular.tickets.presentation.core.BaseViewModel
import com.darkular.tickets.presentation.main.NavigationCommunication
import com.darkular.tickets.presentation.main.NavigationUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateGroupViewModel(
    communication: CreateGroupCommunication,
    private val interactor: CreateGroupInteractor,
    private val navigator: NavigationCommunication
) : BaseViewModel<CreateGroupCommunication, CreateGroupUi>(communication) {

    fun setGroupName(name: String) {
        if (name.trim().isEmpty()) return
        viewModelScope.launch(Dispatchers.IO) {
            if (interactor.saveGroupName(name.trim())) {
                withContext(Dispatchers.Main) {
                    navigator.map(NavigationUi.SecondLevel(R.id.select_group_film_screen))
                }
                init()
            }
            //TODO else handle fail globally
        }
    }

    fun init() {
        viewModelScope.launch(Dispatchers.IO) {
            val groups = interactor.fetchGroups()
            withContext(Dispatchers.Main) { communication.map(CreateGroupUi.Base(groups)) }
        }
    }
}