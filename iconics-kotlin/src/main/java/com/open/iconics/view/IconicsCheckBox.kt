package com.open.iconics.view

import android.content.Context
import android.util.AttributeSet

open class IconicsCheckBox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = androidx.appcompat.R.attr.checkboxStyle
) : IconicsCompoundButton(context, attrs, defStyle) {

    override fun getAccessibilityClassName(): CharSequence {
        return IconicsCheckBox::class.java.name
    }
}
