package com.darkular.tickets.sl

import com.darkular.tickets.sl.core.BaseModule
import com.darkular.tickets.sl.core.CoreModule
import com.darkular.tickets.presentation.main.MainViewModel
import com.darkular.tickets.presentation.main.ScreenContainer

class MainModule(private val coreModule: CoreModule) : BaseModule<MainViewModel> {
    override fun viewModel() = MainViewModel(
        coreModule.navigationCommunication(),
        ScreenContainer.Base(coreModule.provideSharedPreferences())
    )
}