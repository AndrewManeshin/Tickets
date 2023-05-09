package com.darkular.tickets.data.group.create

import android.content.SharedPreferences
import com.darkular.tickets.core.Read
import com.darkular.tickets.core.Save

interface GroupNameContainer: Read<String>, Save<String> {

    class Base(private val sharedPreferences: SharedPreferences): GroupNameContainer {

        override fun read() = sharedPreferences.getString(KEY, "")!!

        override fun save(data: String) {
            sharedPreferences.edit().putString(KEY, data).apply()
        }

        companion object {
            private val KEY = "createGroupName"
        }
    }
}