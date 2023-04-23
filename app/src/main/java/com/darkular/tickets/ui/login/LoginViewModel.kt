package com.darkular.tickets.ui.login

import androidx.lifecycle.viewModelScope
import com.darkular.tickets.domain.login.LoginInteractor
import com.darkular.tickets.ui.core.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    communication: LoginCommunication,
    private val interactor: LoginInteractor,
    private val dispatchersIO: CoroutineDispatcher = Dispatchers.IO,
    private val dispatchersMain: CoroutineDispatcher = Dispatchers.Main,
) : BaseViewModel<LoginCommunication, LoginUi>(communication) {

    fun login(engine: LoginEngine) {
        handleResult { interactor.login(engine) }
    }

    fun init(engine: LoginEngine) {
        if (interactor.authorized())
            handleResult { interactor.signIn(engine) }
        else
            communication.map(LoginUi.Initial)
    }

    private fun handleResult(block: suspend () -> Auth) {
        communication.map(LoginUi.Progress())
        viewModelScope.launch(dispatchersIO) {
            val result = block()
            val resultUi = if (result is Auth.Fail)
                LoginUi.Failed(result.e.message ?: "")//todo improve it
            else
                LoginUi.Success
            withContext(dispatchersMain) { communication.map(resultUi) }
        }
    }
}
