package com.darkular.tickets.sl.core

import android.content.Context
import android.content.SharedPreferences
import com.darkular.tickets.core.FirebaseDatabaseProvider
import com.darkular.tickets.ui.main.NavigationCommunication

interface CoreModule {

    fun provideSharedPreferences(): SharedPreferences
    fun firebaseDatabaseProvider(): FirebaseDatabaseProvider
    fun navigationCommunication() : NavigationCommunication

    class Base(private val context: Context) : CoreModule {
        private val firebaseDatabaseProvider = FirebaseDatabaseProvider.Base()
        private val navigationCommunication = NavigationCommunication.Base()

        override fun provideSharedPreferences(): SharedPreferences =
            context.getSharedPreferences("TicketsAppSharedPref", Context.MODE_PRIVATE)

        override fun firebaseDatabaseProvider() = firebaseDatabaseProvider

        override fun navigationCommunication() = navigationCommunication
    }
}