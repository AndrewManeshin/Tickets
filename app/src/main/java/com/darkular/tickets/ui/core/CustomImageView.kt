package com.darkular.tickets.ui.core

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.squareup.picasso.Picasso

class CustomImageView : androidx.appcompat.widget.AppCompatImageView, AbstractView.Image {

    //region constructors
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    //endregion

    override fun load(url: String) {
        if (url.isNotEmpty()) {
            Picasso.get().load(url).into(this)
            show()
        } else {
            hide()
        }
    }

    override fun show() {
        visibility = View.VISIBLE
    }

    override fun hide() {
        visibility = View.GONE
    }
}