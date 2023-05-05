package com.darkular.tickets.sl.core

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.darkular.tickets.core.FirebaseDatabaseProvider
import com.darkular.tickets.presentation.main.NavigationCommunication

interface CoreModule {

    fun provideSharedPreferences(): SharedPreferences
    fun provideConnectivityManager(): ConnectivityManager
    fun firebaseDatabaseProvider(): FirebaseDatabaseProvider
    fun navigationCommunication() : NavigationCommunication

    class Base(private val context: Context) : CoreModule {
        private val firebaseDatabaseProvider = FirebaseDatabaseProvider.Base()
        private val navigationCommunication = NavigationCommunication.Base()

        override fun provideSharedPreferences(): SharedPreferences =
            context.getSharedPreferences("TicketsAppSharedPref", Context.MODE_PRIVATE)

        override fun provideConnectivityManager(): ConnectivityManager =
            getSystemService(context, ConnectivityManager::class.java)!!

        override fun firebaseDatabaseProvider() = firebaseDatabaseProvider

        override fun navigationCommunication() = navigationCommunication
    }
}