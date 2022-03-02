package com.open.iconics.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.open.iconics.IconicsDrawable
import com.open.iconics.animation.tryToEnableIconicsAnimation
import com.open.iconics.internal.IconicsViewsAttrsApplier

open class IconicsImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatImageView(context, attrs, defStyle) {

    var icon: IconicsDrawable?
        get() = drawable as? IconicsDrawable
        set(icon) = setImageDrawable(tryToEnableIconicsAnimation(icon))

    init {
        //set the scale type for this view
        scaleType = ImageView.ScaleType.CENTER_INSIDE

        icon = IconicsViewsAttrsApplier.getIconicsImageViewDrawable(context, attrs)
    }
}
