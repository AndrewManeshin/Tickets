package com.darkular.tickets.sl

import com.darkular.tickets.sl.core.BaseModule
import com.darkular.tickets.sl.core.CoreModule
import com.darkular.tickets.ui.main.MainViewModel

class MainModule(private val coreModule: CoreModule) : BaseModule<MainViewModel> {
    override fun viewModel() = MainViewModel(
        coreModule.navigationCommunication()
    )
}