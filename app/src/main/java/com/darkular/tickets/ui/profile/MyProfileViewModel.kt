package com.darkular.tickets.ui.profile

import androidx.lifecycle.viewModelScope
import com.darkular.tickets.R
import com.darkular.tickets.data.profile.MyProfileData
import com.darkular.tickets.data.profile.MyProfileRepository
import com.darkular.tickets.ui.core.BaseViewModel
import com.darkular.tickets.ui.main.NavigationCommunication
import com.darkular.tickets.ui.main.NavigationUi
import kotlinx.coroutines.launch

class MyProfileViewModel(
    communication: MyProfileCommunication,
    private val navigation: NavigationCommunication,
    private val repository: MyProfileRepository,
    private val mapper: MyProfileData.MyProfileMapper<MyProfileUi>,
) : BaseViewModel<MyProfileCommunication, MyProfileUi>(
    communication
) {

    fun init() = viewModelScope.launch {
        val data = repository.profile().map(mapper)
        communication.map(data)
    }

    fun signOut() = repository.signOut()
    fun createGroup() = navigation.map(NavigationUi.SecondLevel(R.id.create_group_screen))
}