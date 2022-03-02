package com.open.iconics.internal

import android.widget.TextView
import androidx.core.widget.TextViewCompat
import com.open.iconics.IconicsDrawable

internal class CompoundIconsBundle {
    var startIcon: IconicsDrawable? = null
    var topIcon: IconicsDrawable? = null
    var endIcon: IconicsDrawable? = null
    var bottomIcon: IconicsDrawable? = null

    fun setIcons(textView: TextView) {
        val drawables = TextViewCompat.getCompoundDrawablesRelative(textView)

        TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(
            textView,
            startIcon ?: drawables[0],
            topIcon ?: drawables[1],
            endIcon ?: drawables[2],
            bottomIcon ?: drawables[3]
        )
    }
}
