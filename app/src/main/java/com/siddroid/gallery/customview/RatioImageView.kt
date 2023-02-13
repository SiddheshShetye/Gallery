package com.siddroid.gallery.customview

import android.content.Context
import android.util.AttributeSet


class RatioImageView: androidx.appcompat.widget.AppCompatImageView {

    var ratio = 0f

    constructor(context: Context, attrs: AttributeSet?) :super(context, attrs)

    constructor (context: Context) : super(context)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (ratio != 0f) {
            val width = measuredWidth
            val height = (ratio * width).toInt()
            setMeasuredDimension(width, height)
        }
    }
}