package com.open.iconics.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageButton

open class IconicsImageButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = android.R.attr.buttonStyle
) : IconicsImageView(context, attrs, defStyle) {

    init {
        isFocusable = true
    }

    override fun onSetAlpha(alpha: Int): Boolean = false

    override fun getAccessibilityClassName(): CharSequence = ImageButton::class.java.name
}
