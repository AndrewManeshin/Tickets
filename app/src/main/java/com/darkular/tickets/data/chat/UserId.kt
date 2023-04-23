package com.darkular.tickets.data.chat

import android.content.SharedPreferences
import com.darkular.tickets.core.Read
import com.darkular.tickets.core.Save

class UserId(private val sharedPreferences: SharedPreferences) : Save<String>, Read<String> {

    override fun save(data: String) {
        sharedPreferences.edit().putString(KEY, data).apply()
    }

    override fun read() = sharedPreferences.getString(KEY, "") ?: ""

    private companion object {
        const val KEY = "userIdToChatWith"
    }
}