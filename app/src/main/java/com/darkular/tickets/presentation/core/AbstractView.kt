package com.darkular.tickets.presentation.core

import com.darkular.tickets.presentation.chat.TextMapper

interface AbstractView {

    fun show()
    fun hide()

    interface Text : AbstractView, TextMapper.Void

    interface Image : AbstractView {

        fun load(url: String, isCircle: Boolean = true)
    }
}