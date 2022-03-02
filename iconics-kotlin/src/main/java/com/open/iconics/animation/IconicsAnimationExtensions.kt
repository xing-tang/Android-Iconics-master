package com.open.iconics.animation

import android.view.View
import com.open.iconics.IconicsDrawable

fun View.tryToEnableIconicsAnimation(drawable: IconicsDrawable?): IconicsDrawable? {
    (drawable as? IconicsAnimatedDrawable)?.let { drawable.animateIn(this) }
    return drawable
}

fun View.tryToEnableIconicsAnimation(vararg drawables: IconicsDrawable?) {
    drawables.mapNotNull { (it as? IconicsAnimatedDrawable) }.forEach { it.animateIn(this) }
}