package com.darkular.tickets.data.chat

import android.net.Uri
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.ktx.Firebase


interface MessageData {
    fun messageIsMine(): Boolean
    fun messageBody(): String
    fun wasReadByUser(): Boolean
    fun avatarUri(): String

    @IgnoreExtraProperties
    data class Base(
        val userId: String = "",
        val message: String = "",
        val avatarUrl: String = "",
        val wasRead: Boolean = false,
    ) : MessageData {
        override fun messageIsMine() = userId == Firebase.auth.currentUser?.uid
        override fun messageBody() = message
        override fun wasReadByUser() = wasRead
        override fun avatarUri() = avatarUrl
    }
}