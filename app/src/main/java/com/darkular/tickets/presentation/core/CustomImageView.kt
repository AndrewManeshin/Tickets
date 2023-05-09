package com.darkular.tickets.presentation.core

import android.content.Context
import android.util.AttributeSet
import android.view.View
import coil.load
import coil.transform.CircleCropTransformation
import com.darkular.tickets.R

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

    override fun load(url: String, isCircle: Boolean) {
        if (url.isNotEmpty()) {
            this.load(url) {
                placeholder(R.drawable.ic_image_placeholder)
                if (isCircle) transformations(CircleCropTransformation())
            }
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