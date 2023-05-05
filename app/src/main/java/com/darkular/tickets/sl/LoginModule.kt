package com.darkular.tickets.sl

import com.darkular.tickets.data.login.LoginRepository
import com.darkular.tickets.domain.login.LoginInteractor
import com.darkular.tickets.sl.core.BaseModule
import com.darkular.tickets.sl.core.CoreModule
import com.darkular.tickets.presentation.login.Auth
import com.darkular.tickets.presentation.login.LoginCommunication
import com.darkular.tickets.presentation.login.LoginViewModel

class LoginModule(private val coreModule: CoreModule) : BaseModule<LoginViewModel> {

    override fun viewModel() = LoginViewModel(
        LoginCommunication.Base(),
        LoginInteractor.Base(
            LoginRepository.Base(coreModule.firebaseDatabaseProvider()),
            Auth.AuthResultMapper.Base()
        )
    )
}