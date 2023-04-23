package com.darkular.tickets.data.profile

import com.darkular.tickets.data.login.UserInitial
import com.darkular.tickets.ui.profile.MyProfileUi

interface MyProfileData {

    fun <T> map(mapper: MyProfileMapper<T>): T

    class Base(
        private val userInitial: UserInitial,
        private val canCreateGroups: Boolean
    ) : MyProfileData {
        override fun <T> map(mapper: MyProfileMapper<T>): T {
            return mapper.map(
                userInitial.name,
                "github.com/" + userInitial.login + "\n\n" + userInitial.bio,
                userInitial.photoUrl,
                canCreateGroups
            )
        }
    }

    interface MyProfileMapper<T> {

        fun map(name: String, login: String, photoUrl: String, isGroupCreator: Boolean): T

        class Base : MyProfileMapper<MyProfileUi> {
            override fun map(
                name: String,
                login: String,
                photoUrl: String,
                isGroupCreator: Boolean
            ) = if (isGroupCreator)
                MyProfileUi.GroupCreator(name, login, photoUrl)
            else
                MyProfileUi.Base(name, login, photoUrl)
        }
    }
}