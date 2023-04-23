package com.darkular.tickets.ui.core

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.darkular.tickets.sl.core.App
import com.darkular.tickets.ui.login.LoginActivity
import com.darkular.tickets.ui.main.MainActivity

abstract class BaseActivity : AppCompatActivity() {

    fun <T : ViewModel> viewModel(model: Class<T>, owner: ViewModelStoreOwner) =
        (application as App).viewModel(model, owner)

    fun switchToLogin() = switchTo(LoginActivity::class.java)
    fun switchToMain() = switchTo(MainActivity::class.java)

    private fun switchTo(clasz: Class<*>) {
        startActivity(Intent(this, clasz))
        finish()
    }
}