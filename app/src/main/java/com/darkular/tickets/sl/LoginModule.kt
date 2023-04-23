package com.darkular.tickets.sl

import com.darkular.tickets.data.login.LoginRepository
import com.darkular.tickets.domain.login.LoginInteractor
import com.darkular.tickets.sl.core.BaseModule
import com.darkular.tickets.sl.core.CoreModule
import com.darkular.tickets.ui.login.Auth
import com.darkular.tickets.ui.login.LoginCommunication
import com.darkular.tickets.ui.login.LoginViewModel

class LoginModule(private val coreModule: CoreModule) : BaseModule<LoginViewModel> {

    override fun viewModel() = LoginViewModel(
        LoginCommunication.Base(),
        LoginInteractor.Base(
            LoginRepository.Base(coreModule.firebaseDatabaseProvider()),
            Auth.AuthResultMapper.Base()
        )
    )
}