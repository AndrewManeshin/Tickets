package com.darkular.tickets.ui.core

import com.darkular.tickets.ui.chat.TextMapper

interface AbstractView {

    fun show()
    fun hide()

    interface Text : AbstractView, TextMapper.Void

    interface Image : AbstractView {

        fun load(url: String)
    }
}