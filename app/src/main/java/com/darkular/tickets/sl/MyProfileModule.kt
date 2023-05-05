package com.darkular.tickets.sl

import com.darkular.tickets.data.profile.MyProfileRepository
import com.darkular.tickets.data.profile.MyProfileData
import com.darkular.tickets.sl.core.BaseModule
import com.darkular.tickets.sl.core.CoreModule
import com.darkular.tickets.presentation.profile.MyProfileCommunication
import com.darkular.tickets.presentation.profile.MyProfileViewModel

class MyProfileModule(private val coreModule: CoreModule) : BaseModule<MyProfileViewModel> {

    override fun viewModel() = MyProfileViewModel(
        MyProfileCommunication.Base(),
        coreModule.navigationCommunication(),
        MyProfileRepository.Base(coreModule.firebaseDatabaseProvider()),
        MyProfileData.MyProfileMapper.Base()
    )
}