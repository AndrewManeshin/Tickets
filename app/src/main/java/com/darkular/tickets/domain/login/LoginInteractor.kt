package com.darkular.tickets.domain.login

import com.darkular.tickets.data.login.LoginRepository
import com.darkular.tickets.data.login.UserInitial
import com.darkular.tickets.presentation.login.Auth
import com.darkular.tickets.presentation.login.LoginEngine

interface LoginInteractor {

    fun authorized(): Boolean

    suspend fun login(loginEngine: LoginEngine): Auth
    suspend fun signIn(signIn: LoginEngine): Auth

    class Base(
        private val repository: LoginRepository,
        private val mapper: Auth.AuthResultMapper<UserInitial>,
    ) : LoginInteractor {

        override suspend fun login(loginEngine: LoginEngine): Auth = try {
            val result = loginEngine.login()
            repository.saveUser(result.map(mapper))
            result
        } catch (e: Exception) {
            Auth.Fail(e)
        }

        override suspend fun signIn(signIn: LoginEngine): Auth = try {
            signIn.login()
        } catch (e: Exception) {
            Auth.Fail(e)
        }

        override fun authorized() = repository.user() != null
    }
}