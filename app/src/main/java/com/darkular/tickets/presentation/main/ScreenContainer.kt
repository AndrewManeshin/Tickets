package com.darkular.tickets.presentation.main

import android.content.SharedPreferences
import com.darkular.tickets.R
import com.darkular.tickets.core.Read
import com.darkular.tickets.core.Save

interface ScreenContainer : Read<Int>, Save<Int> {

    class Base(private val sharedPreferences: SharedPreferences): ScreenContainer {

        override fun read() = sharedPreferences.getInt(KEY, R.id.navigation_profile)

        override fun save(data: Int) {
            sharedPreferences.edit().putInt(KEY, data).apply()
        }

        companion object {
            private val KEY = "lastScreen"
        }
    }
}