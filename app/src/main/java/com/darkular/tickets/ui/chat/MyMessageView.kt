package com.darkular.tickets.ui.chat

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.darkular.tickets.R
import com.darkular.tickets.ui.core.AbstractView


class MyMessageView : androidx.appcompat.widget.AppCompatImageView, MessageState {
    //region constructors
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    //endregion

    override fun show(state: MyMessageUiState) {
        val imageResId = when (state) {
            MyMessageUiState.SENT -> R.drawable.ic_done_black_24
            MyMessageUiState.FAILED -> R.drawable.ic_replay_black_24
            MyMessageUiState.READ -> R.drawable.ic_double_check_black_24
            else -> 0
        }
        setImageResource(imageResId)
    }

    override fun show() {
        visibility = View.VISIBLE
    }

    override fun hide() {
        visibility = View.GONE
    }
}

interface MessageState : AbstractView {
    fun show(state: MyMessageUiState)
}